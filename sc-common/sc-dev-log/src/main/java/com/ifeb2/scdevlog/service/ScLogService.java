package com.ifeb2.scdevlog.service;

import org.aspectj.lang.JoinPoint;
import org.springframework.scheduling.annotation.Async;

public interface ScLogService {

    @Async
    void saveLog(String type, String browser, String ip, JoinPoint joinPoint, Object result, String ex, Long time, Long uid);

}
