package com.example.riotApiCrawling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(35); // 기본 스레드 수
        executor.setMaxPoolSize(100); // 최대 스레드 수 이론상 21억개까지 가능.
        executor.setQueueCapacity(500); // 큐 용량
        executor.setThreadNamePrefix("RiotApiThread-"); // 스레드 이름 접두사
        executor.initialize();
        return executor;
    }

}
