package org.example.apikeycrawling.global;

import lombok.*;
import org.example.apikeycrawling.component.ApiKeyCrawlingComponent;
import org.example.apikeycrawling.component.PlayerInfoTestCrawlingComponent;
import org.example.apikeycrawling.component.PlayerMostCrawlingComponent;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyStartupTask implements ApplicationRunner {

    private final ApiKeyCrawlingComponent apiKeyCrawlingComponent;
    private final PlayerInfoTestCrawlingComponent playerInfoTestCrawlingComponent;
    private final PlayerMostCrawlingComponent playerMostCrawlingComponent;

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {

        playerMostCrawlingComponent.crawlingVersion2(0, 100);
    }
}