package com.ifeb2.scdevbase.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@EnableAsync
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

    // 核心线程数为服务器的cpu核心数
    private static final int corePoolSize = Runtime.getRuntime().availableProcessors();
    // 线程池中允许的最大线程数
    private static final int maxPoolSize = 2 * corePoolSize + 1;
    // 工作队列大小
    private static final int queueCapacity = 256;

    @Override
    public Executor getAsyncExecutor() {
        log.info("核心线程数: " + corePoolSize + " 最大线程数: " + maxPoolSize);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Async-Health-Kit-");
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
