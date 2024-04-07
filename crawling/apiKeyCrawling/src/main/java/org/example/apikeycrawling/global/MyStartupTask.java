package org.example.apikeycrawling.global;

import lombok.*;
import org.example.apikeycrawling.component.ApiKeyCrawlingComponent;
import org.example.apikeycrawling.component.PlayerInfoTestCrawlingComponent;
import org.example.apikeycrawling.component.PlayerMostCrawlingComponent;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class MyStartupTask implements ApplicationRunner {

    private final ApiKeyCrawlingComponent apiKeyCrawlingComponent;
    private final PlayerInfoTestCrawlingComponent playerInfoTestCrawlingComponent;
    private final PlayerMostCrawlingComponent playerMostCrawlingComponent;

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {

        // 10000개 씩이다
        // 0 page 1 ~ 1만
        // 1 page 1만 ~ 2만
        // 9 page 9만 ~ 10만
        // 10 page 10만 ~ 11만
        // 40 page 40만 ~ 41만

        // 0 ~ 40 1 ~ 40만
        // 130 ~ 150 : 130만~ 150만

        // 150 ~ 170
        playerMostCrawlingComponent.crawlingVersion2(170, 270);

    }
}