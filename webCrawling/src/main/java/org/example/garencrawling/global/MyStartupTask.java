package org.example.garencrawling.global;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.example.garencrawling.mostchampion.domain.Champion;
import org.example.garencrawling.mostchampion.repository.ChampionRepository;
import org.openqa.selenium.PageLoadStrategy;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;


import java.time.Duration;
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

        List<Champion> champions = championRepository.findAll();
        for (Champion champion : champions)
            championNames.put(champion.getName(), champion.getId());

        WebDriverManager.chromedriver().setup();

        Proxy proxy;

        proxy = new Proxy();
        proxy.setHttpProxy("43.201.36.62:3128");
        proxy.setSslProxy("43.201.36.62:3128");
        proxyList.add(proxy);

        proxy = new Proxy();
        proxy.setHttpProxy("j10a605.p.ssafy.io:3128");
        proxy.setSslProxy("j10a605.p.ssafy.io:3128");
        proxyList.add(proxy);

        proxy = new Proxy();
        proxy.setHttpProxy(	"115.144.140.64:8302");
        proxy.setSslProxy("115.144.140.64:8302");
        proxyList.add(proxy);

        for (int i = 0; i <= proxyList.size(); i++) {
            ChromeOptions options = new ChromeOptions();
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
            options.addArguments("--disable-blink-features=AutomationControlled");
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";
            options.addArguments("--user-agent=" + userAgent);
            options.setPageLoadStrategy(PageLoadStrategy.NONE);

            if (i != proxyList.size()) {
                options.setProxy(proxyList.get(i));
            }

            optionsList.add(options);
        }

        for (int i = 0; i < threadSize; i++) {
            ChromeDriver driver = new ChromeDriver(GlobalConstants.optionsList.get(i / optionsList.size()));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.waitTime));
            drivers.add(driver);
            waits.add(wait);
        }

        System.out.println("End MyStartupTask");
    }
}