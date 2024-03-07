package org.example.garencrawling.mostchampion.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.example.garencrawling.global.MyException;
import org.example.garencrawling.mostchampion.domain.MostData;
import org.example.garencrawling.mostchampion.domain.PlayerInfo;
import org.example.garencrawling.mostchampion.domain.PlayerPrevSoloRank;
import org.example.garencrawling.mostchampion.repository.PlayerPrevSoloRankRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
@EnableMongoRepositories(basePackageClasses = {PlayerPrevSoloRankRepository.class})
public class AsyncService {

    private final PlayerPrevSoloRankRepository playerPrevSoloRankRepository;

    int saveSize = 50;

    @Async("threadPoolTaskExecutor")
    public void processPlayersInRange(int startPlayerId, int endPlayerId, List<PlayerInfo> savedPlayerInfos) {

        int currentStartPlayerId = startPlayerId;
        while (currentStartPlayerId <= endPlayerId) {
            int currentEndPlayerId = Math.min(currentStartPlayerId + saveSize - 1, endPlayerId);

            List<PlayerPrevSoloRank> savedPlayerPrevSoloRanks = playerPrevSoloRankRepository.findAll();

            List<PlayerInfo> playerInfos = new ArrayList<>();
            for (int i = currentStartPlayerId; i <= currentEndPlayerId; i++) {

                int playerId = savedPlayerInfos.get(i).getPlayerId();

                boolean exists = savedPlayerPrevSoloRanks.stream()
                        .anyMatch(rank -> rank.getPlayerId() == playerId);

                if (!exists) {
                    playerInfos.add(savedPlayerInfos.get(i - 1));
                }
            }

            crawling(playerInfos);
            currentStartPlayerId = currentEndPlayerId + 1;
        }

    }

    public void crawling(List<PlayerInfo> playerInfos) {
        WebElement element;
        ChromeOptions options;
        ChromeDriver driver;
        WebDriverWait wait;
        List<WebElement> rows;
        ArrayList<PlayerPrevSoloRank> playerPrevSoloRanks = new ArrayList<>();

        ////////////////////////////////////////////////////////////////////////////////////////////

        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();

        options.addArguments("--headless"); // headless 모드 활성화
        options.addArguments("--disable-gpu"); // 최신 버전에서는 필요하지 않지만 호환성을 위해 추천
        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // 명시적 대기 시간 설정

        ////////////////////////////////////////////////////////////////////////////////////////////

        for (int index = 0; index < playerInfos.size(); index++) {

            PlayerInfo playerInfo = playerInfos.get(index);

            PlayerPrevSoloRank playerPrevSoloRank = new PlayerPrevSoloRank();
            playerPrevSoloRank.setPlayerId(playerInfo.getPlayerId());
            System.out.print("playerInfo.getPlayerId() = " + playerInfo.getPlayerId() + " ");

            driver.get("https://www.op.gg/summoners/kr/" + playerInfo.getUserNickname() + "/champions");

            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/header")));

                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[4]/div[1]/div/div[1]/div[2]/div[2]/div/h1/strong")));

                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[4]/div[1]/div/div[1]/div[2]/div[1]")));

                        StringTokenizer st = new StringTokenizer(driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/div[1]/div[2]/div[1]/div/ul/li[1]/div")).getText());

                        String season1 = st.nextToken(); // S2023
                        if (!season1.equals("S2023")) {
                            throw new MyException("시즌[0]이 S2023이 아닙니다.");
                        }

                        String season2 = st.nextToken(); // S2
                        if (!season2.equals("S2")) {
                            throw new MyException("시즌[0]이 S2가 아닙니다.");
                        }

                        String tier = st.nextToken();
                        playerPrevSoloRank.setTier(tier);

                        if (tier.equals("Master") || tier.equals("Grandmaster") || tier.equals("Challenger")) {

                        } else {
                            playerPrevSoloRank.setRankNum(st.nextToken());
                        }

                        try {
                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[6]/div/div/div[1]/div[2]/div/button")));
                            element = driver.findElement(By.xpath("/html/body/div[1]/div[6]/div/div/div[1]/div[2]/div/button"));
                            driver.executeScript("arguments[0].click();", element);

                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[6]/div/div/div[1]/div[2]/div/div/button[2]")));
                            element = driver.findElement(By.xpath("/html/body/div[1]/div[6]/div/div/div[1]/div[2]/div/div/button[2]"));
                            driver.executeScript("arguments[0].click();", element);

                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[6]/div/div/div[2]/button[2]")));
                            element = driver.findElement(By.xpath("/html/body/div[1]/div[6]/div/div/div[2]/button[2]"));
                            driver.executeScript("arguments[0].click();", element);

                            try {
                                Thread.sleep(2000);

                                rows = driver.findElements(By.xpath("/html/body/div[1]/div[6]/div/table/tbody/tr"));
                                for (WebElement row : rows) {
                                    List<WebElement> cols = row.findElements(By.tagName("td"));

                                    MostData mostData = new MostData();

                                    for (int i = 0; i < cols.size(); i++) {
                                        WebElement col = cols.get(i);
                                        String tmp = col.getText().replace("\n", "").replace(" ", "");

                                        if (tmp.equals("Therearenoresultsrecorded.")) {
                                            throw new MyException("Therearenoresultsrecorded.");
                                        }

                                        if (i == 0)
                                            mostData.setMostSeq(tmp);
                                        else if (i == 1)
                                            mostData.setChampion(tmp);
                                        else if (i == 2)
                                            mostData.setGame(tmp);
                                        else if (i == 3)
                                            mostData.setRating(tmp);
                                        else if (i == 4)
                                            mostData.setGold(tmp);
                                        else if (i == 5)
                                            mostData.setCreepScore(tmp);
                                        else if (i == 6)
                                            mostData.setMaxKills(tmp);
                                        else if (i == 7)
                                            mostData.setMaxDeaths(tmp);
                                        else if (i == 8)
                                            mostData.setAverageDamageDealt(tmp);
                                        else if (i == 9)
                                            mostData.setAverageDamageTaken(tmp);
                                        else if (i == 10)
                                            mostData.setDoubleKills(tmp);
                                        else if (i == 11)
                                            mostData.setTripleKills(tmp);
                                        else if (i == 12)
                                            mostData.setQuadraKills(tmp);
                                        else if (i == 13)
                                            mostData.setPentaKills(tmp);
                                    }
                                    playerPrevSoloRank.getMostDatas().add(mostData);
                                }

                                System.out.print("성공");
                                System.out.println(" " + playerInfo.getUserNickname());
                            } catch (Exception exception) {
                                System.out.print("driver 모스트 목록 Exception - 예상치 못한 오류 - 재시도");
                                System.out.println(" " + playerInfo.getUserNickname());
                                index--;
                                continue;
                            }

                        } catch (TimeoutException e2) {
                            System.out.print("driver 시즌 버튼 TimeoutException - 버튼이 안 찾아짐 - 재시도");
                            System.out.println(" " + playerInfo.getUserNickname());
                            index--;
                            continue;
                        } catch (Exception e2) {
                            System.out.print("driver 시즌 버튼 Exception - 예상치 못한 오류 - 재시도");
                            System.out.println(" " + playerInfo.getUserNickname());
                            index--;
                            continue;
                        }
                    } catch (TimeoutException e2) {
                        System.out.print("find 시즌 목록 TimeoutException - 로딩 실패 - 재시도");
                        System.out.println(" " + playerInfo.getUserNickname());
                        index--;
                        continue;
                    } catch (NoSuchElementException e2) {
                        System.out.print("find 시즌 목록 NoSuchElementException - 티어 목록 없음");
                        System.out.println(" " + playerInfo.getUserNickname());
                        playerPrevSoloRank.setTier(null);
                        playerPrevSoloRank.setRankNum(null);
                        playerPrevSoloRank.setMostDatas(null);
                    } catch (MyException e2) {
                        System.out.print("find 시즌 목록 MyException - 티어 틀림 " + e2.getMessage());
                        System.out.println(" " + playerInfo.getUserNickname());
                        playerPrevSoloRank.setTier(null);
                        playerPrevSoloRank.setRankNum(null);
                        playerPrevSoloRank.setMostDatas(null);
                    }
                } catch (TimeoutException e) {
                    try {
                        if (driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/h2")).getText().equals("OP.GG에 등록되지 않은 소환사입니다. 오타를 확인 후 다시 검색해주세요.")) {
                            System.out.print("find 닉네임 TimeoutException - 없는 닉네임");
                            System.out.println(" " + playerInfo.getUserNickname());
                            playerPrevSoloRank.setTier(null);
                            playerPrevSoloRank.setRankNum(null);
                            playerPrevSoloRank.setMostDatas(null);
                        }
                    } catch (NoSuchElementException e2) {
                        System.out.print("find 닉네임 TimeoutException - NoSuchElementException - 로딩 실패 - 재시도");
                        System.out.println(" " + playerInfo.getUserNickname());
                        index--;
                        continue;
                    }
                } catch (Exception e) {
                    System.out.print("find 닉네임 Exception - 예상치 못한 오류 - 재시도");
                    System.out.println(" " + playerInfo.getUserNickname());
                    index--;
                    continue;
                }

            } catch (TimeoutException e) {
                System.out.print("find header TimeoutException - 429 error - 재시도");
                System.out.println(" " + playerInfo.getUserNickname());
                index--;
                continue;
            } catch (Exception e) {
                System.out.print("find header Exception - 예상치 못한 오류 - 재시도");
                System.out.println(" " + playerInfo.getUserNickname());
                index--;
                continue;
            }
            playerPrevSoloRanks.add(playerPrevSoloRank);
        }

        driver.quit();
        playerPrevSoloRankRepository.saveAll(playerPrevSoloRanks);
    }
}