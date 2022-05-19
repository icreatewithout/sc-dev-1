package com.ifeb2.scdevlog.aspect;

import com.ifeb2.scdevbase.utils.Constants;
import com.ifeb2.scdevbase.utils.RequestHolder;
import com.ifeb2.scdevbase.utils.SecurityUtils;
import com.ifeb2.scdevlog.service.ScLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@Aspect
@Component
public class LogAspect {

    private final ScLogService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(ScLogService logService) {
        this.logService = logService;
    }


    @Pointcut("@annotation(com.ifeb2.scdevlog.annotation.RecordLog)")
    public void logPointcut() {

    }

    @Before("logPointcut()")
    public void before() {
        currentTime.set(System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        Long uid = SecurityUtils.getUserId();
        Long time = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.saveLog("1", Constants.getBrowser(request), Constants.getIp(request), joinPoint, result, null, time, uid);
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Long uid = SecurityUtils.getUserId();
        Long time = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.saveLog("2", Constants.getBrowser(request), Constants.getIp(request), joinPoint, null, getStackTrace(e), time, uid);
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }


}
