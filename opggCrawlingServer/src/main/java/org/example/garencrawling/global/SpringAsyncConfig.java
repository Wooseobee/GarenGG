package org.example.garencrawling.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // Application이 아닌, Async 설정 클래스에 붙여야 함.
public class SpringAsyncConfig {

    public static int corePoolSize = 10;
    public static int maxPoolSize = 100;
    public static int queueCapacity = 200;

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize); // 기본 스레드 수
        taskExecutor.setMaxPoolSize(maxPoolSize); // 최대 스레드 수
        taskExecutor.setQueueCapacity(queueCapacity); // Queue 사이즈
        taskExecutor.setThreadNamePrefix("Executor-");
        return taskExecutor;
    }

}