package org.example.garencrawling.global;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.example.garencrawling.mostchampion.domain.Champion;
import org.example.garencrawling.mostchampion.repository.ChampionRepository;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.garencrawling.global.GlobalConstants.*;

@Component
@RequiredArgsConstructor
public class MyStartupTask implements ApplicationRunner {

    private final ChampionRepository championRepository;

    @Override
    public void run(ApplicationArguments args) {

        System.out.println("corePoolSize = " + corePoolSize);
        System.out.println("maxPoolSize = " + maxPoolSize);
        System.out.println("queueCapacity = " + queueCapacity);

        System.out.println("threadSize = " + threadSize);
        System.out.println("saveSize = " + saveSize);
        System.out.println("waitTime = " + waitTime);

        System.out.println("tryMaxCount = " + tryMaxCount);

        List<Champion> champions = championRepository.findAll();
        for (int i = 0; i < champions.size(); i++) {
            championNames.put(champions.get(i).getName(), champions.get(i).getId());
        }

        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();

        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.managed_default_content_settings.images", 2);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-useAutomationExtension");
        options.addArguments("--no-sandbox");

        options.addArguments("--disk-cache-size=4096");
        options.addArguments("--dns-prefetch-disable");

        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
    }
}