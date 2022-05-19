package com.ifeb2.scdevlog.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifeb2.scdevbase.utils.Constants;
import com.ifeb2.scdevbase.utils.StreamConstants;
import com.ifeb2.scdevlog.annotation.RecordLog;
import com.ifeb2.scdevlog.dto.ScLogDto;
import com.ifeb2.scdevlog.service.ScLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScLogServiceImpl implements ScLogService {

    private final StreamBridge streamBridge;

    @Override
    public void saveLog(String type, String browser, String ip, JoinPoint joinPoint, Object result, String ex, Long time, Long uid) {

        ScLogDto scLogDto = new ScLogDto();
        scLogDto.setTime(time);
        scLogDto.setLogType(type);

        if (null != result) {
            try {
                scLogDto.setResult(new ObjectMapper().writeValueAsString(result));
            } catch (Exception e) {
                log.error("异常信息：{}", e.getMessage());
            }
        }

        if (null != ex) {
            scLogDto.setExceptionDetail(ex.getBytes(StandardCharsets.UTF_8));
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        RecordLog recordLog = method.getAnnotation(RecordLog.class);
        scLogDto.setDescriptions(recordLog.value());
        scLogDto.setBizName(recordLog.bizName());

        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
        StringBuilder params = new StringBuilder("{");
        List<Object> argValues = new ArrayList<>(Arrays.asList(joinPoint.getArgs()));
        for (Object argValue : argValues) {
            params.append(argValue).append(" ");
        }

        scLogDto.setRequestIp(ip);
        scLogDto.setAddress(Constants.getCityInfoByIp(ip));
        scLogDto.setMethod(methodName);
        scLogDto.setParams(params + "}");
        scLogDto.setBrowser(browser);
        scLogDto.setCreateUser(uid);
        //推到消息队列处理
        streamBridge.send(StreamConstants.RECORD_LOG_EVENT, scLogDto);
    }

}
