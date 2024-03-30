package org.example.apikeycrawling;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.example.apikeycrawling.dto.AccountDto;
import org.example.apikeycrawling.entity.mysql.ApiKey;
import org.example.apikeycrawling.repository.ApiKeyRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ApiKeyCrawlingComponent {

    private final ApiKeyRepository apiKeyRepository;

    public void function1() throws InterruptedException {
        ChromeOptions options;
        String userAgent;
        ChromeDriver driver;
        WebElement webElement;

        ///////////////////////////////////////////////////////////////////

        WebDriverManager.chromedriver().setup();

        options = new ChromeOptions();
        options.addArguments("--disable-gpu");
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
        userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";
        options.addArguments("--user-agent=" + userAgent);

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        //////////////////////////////////////////////////////////////////////////////////////////////////////

        driver.get("https://developer.riotgames.com/apis");
        Thread.sleep(2000);

        webElement = driver.findElement(By.xpath("/html/body/nav/div/div[2]/div[1]/ul[2]/li/a"));
        driver.executeScript("arguments[0].click();", webElement);
        Thread.sleep(60000);

        int count = 0;
        while (true) {

            String uuid = UUID.randomUUID().toString();

            driver.get("https://developer.riotgames.com/app-type");
            Thread.sleep(2000);

            webElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[4]/div/div[2]/div/button"));
            driver.executeScript("arguments[0].click();", webElement);
            Thread.sleep(2000);

            webElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[5]/div/div/div[3]/a[1]"));
            driver.executeScript("arguments[0].click();", webElement);
            Thread.sleep(2000);

            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/form/div[1]/div[1]/div/input")).sendKeys(uuid);
            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/form/div[1]/div[2]/div/textarea")).sendKeys(uuid);
            Thread.sleep(2000);

            webElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/form/div[3]/input"));
            driver.executeScript("arguments[0].click();", webElement);
            Thread.sleep(2000);

            String key = driver.findElement(By.xpath("/html/body/div[2]/div/form/div/div/div[5]/ul/li[6]/span")).getText();
            Thread.sleep(2000);

            ApiKey apiKey = new ApiKey();
            apiKey.setApiKey(key);
            apiKey.setUuid(uuid);

            apiKeyRepository.save(apiKey);
            count++;
            System.out.println("count = " + count);
        }
    }

    public void function2() {

        List<ApiKey> apiKeys = apiKeyRepository.findAll();

        int count = 0;
        while (true) {
            for (int i = 0; i < apiKeys.size(); i++) {
                count++;

                RestTemplate restTemplate = new RestTemplate();
                String url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}?api_key={apiKey}";
                AccountDto accountDto = restTemplate.getForObject(url, AccountDto.class, "틀딱기", "KR1", apiKeys.get(i).getApiKey());
                System.out.println("count = " + count + " i = " + i + " " + accountDto.getPuuid());

            }
        }

    }
}
