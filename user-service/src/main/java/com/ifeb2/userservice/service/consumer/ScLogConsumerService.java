package com.ifeb2.userservice.service.consumer;

import com.ifeb2.scdevlog.dto.ScLogDto;
import com.ifeb2.userservice.domain.ScLog;
import com.ifeb2.userservice.service.ScLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScLogConsumerService {

    private final ScLogService scLogService;

    @Bean
    public Consumer<ScLogDto> recordLog() {
        return logDto -> {
            log.info("received: {}", logDto);
            ScLog scLog = new ScLog();
            BeanUtils.copyProperties(logDto,scLog);
            scLogService.saveLog(scLog);
        };
    }
}
