package org.example.apikeycrawling;

import lombok.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyStartupTask implements ApplicationRunner {


    private final ApiKeyCrawlingComponent apiKeyCrawlingComponent;
    private final PlayerMostCrawlingComponent playerMostCrawlingComponent;

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        playerMostCrawlingComponent.function1();

    }


}