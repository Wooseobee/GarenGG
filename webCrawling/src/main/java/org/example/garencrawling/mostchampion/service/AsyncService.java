package org.example.garencrawling.mostchampion.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.example.garencrawling.global.GlobalConstants;
import org.example.garencrawling.mostchampion.domain.MostData;
import org.example.garencrawling.mostchampion.domain.PlayerCurSoloRank;
import org.example.garencrawling.mostchampion.domain.PlayerInfo;
import org.example.garencrawling.mostchampion.repository.PlayerCurSoloRankRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
@EnableMongoRepositories(basePackageClasses = {PlayerCurSoloRankRepository.class})
public class AsyncService {

    private final PlayerCurSoloRankRepository playerCurSoloRankRepository;

    @Async("threadPoolTaskExecutor")
    public void processPlayersInRange(List<PlayerInfo> subFindedPlayerInfos, int threadNumber) throws InterruptedException {

        System.out.println("threadNumber = " + threadNumber + " 시작");

        int currentStartIndex = 0;
        int endIndex = subFindedPlayerInfos.size() - 1;

        while (currentStartIndex <= endIndex) {
            int currentEndIndex = Math.min(currentStartIndex + GlobalConstants.saveSize - 1, endIndex);

            crawling(subFindedPlayerInfos.subList(currentStartIndex, currentEndIndex + 1), threadNumber);
            currentStartIndex = currentEndIndex + 1;
        }

        System.out.println("threadNumber = " + threadNumber + " 종료");
    }

    public void crawling(List<PlayerInfo> playerInfos, int threadNumber) throws InterruptedException {
        WebElement element;
        ChromeOptions options;
        ChromeDriver driver;
        WebDriverWait wait;
        List<WebElement> rows;
        ArrayList<PlayerCurSoloRank> playerCurSoloRanks = new ArrayList<>();

        ////////////////////////////////////////////////////////////////////////////////////////////

        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();

        options.addArguments("--headless"); // headless 모드 활성화
        options.addArguments("--disable-gpu"); // 최신 버전에서는 필요하지 않지만 호환성을 위해 추천
        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.waitTime)); // 명시적 대기 시간 설정

        ////////////////////////////////////////////////////////////////////////////////////////////

        for (int index = 0; index < playerInfos.size(); index++) {
            StringBuilder sb = new StringBuilder();
            sb.append("threadNumber = ").append(threadNumber).append(" ");

            PlayerInfo playerInfo = playerInfos.get(index);
            sb.append("playerInfo.getPlayerId() = ").append(playerInfo.getPlayerId()).append(" ");
            sb.append("playerInfo.getUserNickname() = ").append(playerInfo.getUserNickname()).append(" ");

            PlayerCurSoloRank playerCurSoloRank = new PlayerCurSoloRank();
            playerCurSoloRank.setPlayerId(playerInfo.getPlayerId());

            driver.get("https://fow.kr/find/" + playerInfo.getUserNickname());

            try {
                // 갱신 가능 버튼
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".refresh_btn.refresh_sid")));
                element = driver.findElement(By.cssSelector(".refresh_btn.refresh_sid"));
                driver.executeScript("arguments[0].click();", element);
                Thread.sleep(5000);
            } catch (Exception e) {
                try {
                    // 갱신 불가능 버튼
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".refresh_btn")));
                } catch (Exception e2) {
                    sb.append("갱신 불가능 버튼 없음");
                    System.out.println(sb);
                    continue;
                }
            }
            try {
                // 현재 티어
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[5]/div[1]/div[1]/div[5]/div[1]/div[2]/b")));
                String line = driver.findElement(By.xpath("/html/body/div[5]/div[1]/div[1]/div[5]/div[1]/div[2]/b")).getText();
                StringTokenizer st = new StringTokenizer(line);

                String tier = st.nextToken();

                if (tier.equals("배치")) {
                    sb.append("배치 유저임");
                    System.out.println(sb);
                    continue;
                }

                tier = tier.toLowerCase();
                tier = Character.toUpperCase(tier.charAt(0)) + tier.substring(1);
                playerCurSoloRank.setTier(tier);

                if (tier.equals("Master") || tier.equals("Grandmaster") || tier.equals("Challenger")) {

                } else {
                    String rankNum = st.nextToken();
                    if (rankNum.equals("I")) {
                        playerCurSoloRank.setRankNum("1");
                    } else if (rankNum.equals("II")) {
                        playerCurSoloRank.setRankNum("2");
                    } else if (rankNum.equals("III")) {
                        playerCurSoloRank.setRankNum("3");
                    } else if (rankNum.equals("IV")) {
                        playerCurSoloRank.setRankNum("4");
                    }
                }
            } catch (TimeoutException e) {
                sb.append("현재 티어 없음");
                System.out.println(sb);
                continue;
            }

            try {
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".sbtn.rankchamp_list.rankchamp_list_solo")));
                element = driver.findElement(By.cssSelector(".sbtn.rankchamp_list.rankchamp_list_solo"));
                driver.executeScript("arguments[0].click();", element);
            } catch (Exception e) {
                sb.append("솔로랭크 버튼 없음");
                System.out.println(sb);
                continue;
            }

            try {
                // 챔피언 목록들

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[5]/div[1]/div[1]/div[8]/table/tbody/tr")));
                rows = driver.findElements(By.xpath("/html/body/div[5]/div[1]/div[1]/div[8]/table/tbody/tr"));

                if (!driver.findElement(By.xpath("/html/body/div[5]/div[1]/div[1]/div[8]/table/thead/tr/th[1]")).getText().equals("챔피언(S14_1 솔로랭크)")) {
                    sb.append("S14_1 솔로랭크 없음");
                    System.out.println(sb);
                    continue;
                }

                int mostSeq = 1;
                for (WebElement row : rows) {
                    List<WebElement> cols = row.findElements(By.tagName("td"));

                    MostData mostData = new MostData();
                    mostData.setMostSeq(String.valueOf(mostSeq));
                    mostSeq++;

                    for (int i = 0; i < cols.size(); i++) {
                        String tmp = cols.get(i).getText();

                        if (i == 0) {
                            mostData.setChampion(GlobalConstants.championNames.get(tmp));
                        } else if (i == 1) {
                            mostData.setGame(tmp);
                        } else if (i == 2) {
                            double percentage = Double.parseDouble(tmp.substring(0, tmp.length() - 1));
                            int win = (int) (Integer.parseInt(mostData.getGame()) * percentage) / 100;
                            int lose = Integer.parseInt(mostData.getGame()) - win;

                            String result = "";

                            if (win != 0)
                                result = result + win + "W";
                            if (lose != 0)
                                result = result + lose + "L";

                            result = result + (int) percentage + "%";

                            mostData.setGame(result);
                        }
                    }
                    playerCurSoloRank.getMostDatas().add(mostData);
                }
                sb.append("----------------------성공");
                System.out.println(sb);
                playerCurSoloRanks.add(playerCurSoloRank);

            } catch (Exception e) {
                sb.append("챔피언 목록들 없음");
                System.out.println(sb);
                continue;
            }
        }
        driver.quit();
        playerCurSoloRankRepository.saveAll(playerCurSoloRanks);
    }
}