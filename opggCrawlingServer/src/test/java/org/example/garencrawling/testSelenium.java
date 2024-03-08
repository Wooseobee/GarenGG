package org.example.garencrawling;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.garencrawling.mostchampion.domain.MostData;
import org.example.garencrawling.mostchampion.domain.PlayerPrevSoloRank;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class testSelenium {

    public static String[] userNicknames = new String[]{
            "진뚱이용-KR1",
            "Hide on bush-KR1",
            "와진짜오랜만이다-머쓱타드"
    };

    public static void main(String[] args) {
        WebElement element;
        ChromeOptions options;
        ChromeDriver driver;
        WebDriverWait wait;
        List<WebElement> rows;

        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();

        options.addArguments("--disable-gpu"); // 최신 버전에서는 필요하지 않지만 호환성을 위해 추천

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // 명시적 대기 시간 설정

        ////////////////////////////////////////////////////////////////////////////////////////////

        for (String userNickname : userNicknames) {
            try {
                driver.get("https://www.op.gg/summoners/kr/" + userNickname + "/champions");

                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[6]/div/div/div[2]/button[2]")));
                element = driver.findElement(By.xpath("/html/body/div[1]/div[6]/div/div/div[2]/button[2]"));
                driver.executeScript("arguments[0].click();", element);

                try {
                    wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("/html/body/div[1]/div[6]/div/table/tbody/tr[1]/td[1]"), "1"));

                    rows = driver.findElements(By.xpath("/html/body/div[1]/div[6]/div/table/tbody/tr"));

                    System.out.println("rows.size() = " + rows.size());
                }
                // 로딩 실패 or 기록된 전적이 없습니다.
                catch (TimeoutException e) {

                    // 기록된 전적이 없습니다.
                    try {

                        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("/html/body/div[1]/div[6]/div/table/tbody/tr/td/div/div/p"), "기록된 전적이 없습니다."));

                        System.out.println(driver.findElement(By.xpath("/html/body/div[1]/div[6]/div/table/tbody/tr/td/div/div/p")).getText());

                    }
                    // 로딩 실패
                    catch (TimeoutException e2) {

                        System.out.println("챔피언 목록 - 429 error");
                        continue;

                    }


                }
                // 이상한 에러
                catch (StaleElementReferenceException e) {
                    System.out.println("챔피언 목록 - StaleElementReferenceException");
                    continue;
                }
            }
            // 로딩 실패
            catch (TimeoutException e) {
                System.out.println("챔피언 목록 접속 - 429 error");
                continue;
            }
        }

        driver.quit();
    }
}
