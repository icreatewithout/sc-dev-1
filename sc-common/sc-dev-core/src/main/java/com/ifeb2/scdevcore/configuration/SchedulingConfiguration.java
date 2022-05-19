package com.ifeb2.scdevcore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulingConfiguration {

    // 核心线程数为服务器的cpu核心数
    private static final int corePoolSize = Runtime.getRuntime().availableProcessors();
    // 线程池中允许的最大线程数
    private static final int maxPoolSize = 2 * corePoolSize + 1;

    /**
     * 定时器开启 线程池 设置大小
     *
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(corePoolSize);
        taskScheduler.initialize();
        return taskScheduler;
    }

    /**
     * More than one TaskExecutor bean found within the context, and none is named 'taskExecutor'.  开启 线程池
     *
     * @return
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.initialize();
        return taskExecutor;
    }

}
