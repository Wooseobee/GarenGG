package org.example.garencrawling.mostchampion.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.example.garencrawling.global.MyException;
import org.example.garencrawling.mostchampion.domain.MostData;
import org.example.garencrawling.mostchampion.domain.PlayerInfo;
import org.example.garencrawling.mostchampion.domain.PlayerPrevSoloRank;
import org.example.garencrawling.mostchampion.repository.PlayerPrevSoloRankRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@EnableMongoRepositories(basePackageClasses = {PlayerPrevSoloRankRepository.class})
public class AsyncService {

    private final PlayerPrevSoloRankRepository playerPrevSoloRankRepository;
    public final String[] proxyAddress = new String[]{
            "localhost",
            "52.79.226.142:3128",
            "15.164.142.18:3128",
            "13.209.64.11:3128",
    };

    int saveSize = 20;

    @Async("threadPoolTaskExecutor")
    public void processPlayersInRange(int startPlayerId, int endPlayerId, List<PlayerInfo> savedPlayerInfos, int threadNumber) {

        int currentStartPlayerId = startPlayerId;
        while (currentStartPlayerId <= endPlayerId) {
            int currentEndPlayerId = Math.min(currentStartPlayerId + saveSize - 1, endPlayerId);

            List<PlayerInfo> tmpPlayerInfos = new ArrayList<>();
            for (int i = currentStartPlayerId; i <= currentEndPlayerId; i++) {
                PlayerInfo currentSavedPlayerInfo = savedPlayerInfos.get(i - 1);
                tmpPlayerInfos.add(currentSavedPlayerInfo);
            }

            crawling(tmpPlayerInfos, threadNumber);
            currentStartPlayerId = currentEndPlayerId + 1;
        }

    }

    public void crawling(List<PlayerInfo> playerInfos, int threadNumber) {
        WebElement element;
        ChromeOptions options;
        ChromeDriver driver;
        WebDriverWait wait;
        List<WebElement> rows;
        ArrayList<PlayerPrevSoloRank> playerPrevSoloRanks = new ArrayList<>();

        ////////////////////////////////////////////////////////////////////////////////////////////

        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();

        if (threadNumber - 1 != 0) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyAddress[threadNumber - 1])
                    .setSslProxy(proxyAddress[threadNumber - 1]);
            options.setProxy(proxy);
        }
        options.addArguments("--headless"); // headless 모드 활성화
        options.addArguments("--disable-gpu"); // 최신 버전에서는 필요하지 않지만 호환성을 위해 추천
        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // 명시적 대기 시간 설정

        ////////////////////////////////////////////////////////////////////////////////////////////

        for (int index = 0; index < playerInfos.size(); index++) {
            PlayerInfo playerInfo = playerInfos.get(index);

            StringBuilder sb = new StringBuilder();
            sb.append("threadNumber = ").append(threadNumber).append(" ");
            sb.append("playerInfo.getPlayerId() = ").append(playerInfo.getPlayerId()).append(" ");
            sb.append("playerInfo.getUserNickname() = ").append(playerInfo.getUserNickname()).append(" ");

            PlayerPrevSoloRank playerPrevSoloRank = new PlayerPrevSoloRank();
            playerPrevSoloRank.setPlayerId(playerInfo.getPlayerId());

            driver.get("https://www.op.gg/summoners/kr/" + playerInfo.getUserNickname());
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[7]/div[1]/div[1]")));

                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("tier")));

                    StringTokenizer st = new StringTokenizer(driver.findElement(By.className("tier")).getText());
                    String tier = st.nextToken();
                    playerPrevSoloRank.setTier(tier);

                    if (tier.equals("Master") || tier.equals("Grandmaster") || tier.equals("Challenger")) {

                    } else {
                        playerPrevSoloRank.setRankNum(st.nextToken());
                    }

                    try {
                        driver.get("https://www.op.gg/summoners/kr/" + playerInfo.getUserNickname() + "/champions");

                        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[6]/div/div/div[2]/button[2]")));
                        element = driver.findElement(By.xpath("/html/body/div[1]/div[6]/div/div/div[2]/button[2]"));
                        driver.executeScript("arguments[0].click();", element);

                        try {
                            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("/html/body/div[1]/div[6]/div/table/tbody/tr[1]/td[1]"), "1"));

                            rows = driver.findElements(By.xpath("/html/body/div[1]/div[6]/div/table/tbody/tr"));

                            for (WebElement row : rows) {
                                List<WebElement> cols = row.findElements(By.tagName("td"));

                                MostData mostData = new MostData();

                                for (int i = 0; i < cols.size(); i++) {
                                    WebElement col = cols.get(i);
                                    String tmp = col.getText().replace("\n", "").replace(" ", "");

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
                            sb.append("----------------------성공");
//                            System.out.println(sb);
                        }
                        // 로딩 실패 or 기록된 전적이 없습니다.
                        catch (TimeoutException e) {

                            // 기록된 전적이 없습니다.
                            try {
                                wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("/html/body/div[1]/div[6]/div/table/tbody/tr/td/div/div/p"), "기록된 전적이 없습니다."));
                                sb.append("챔피언 목록 - 기록된 전적이 없습니다.");
                                System.out.println(sb);
                                playerPrevSoloRank.setTier(null);
                                playerPrevSoloRank.setRankNum(null);
                                playerPrevSoloRank.setMostDatas(null);
                            }
                            // 로딩 실패
                            catch (TimeoutException e2) {
                                sb.append("챔피언 목록 - 429 error");
                                System.out.println(sb);
                                index--;
                                continue;
                            }
                        }
                        // 이상한 에러
                        catch (StaleElementReferenceException e) {
                            sb.append("챔피언 목록 - StaleElementReferenceException");
                            System.out.println(sb);
                            index--;
                            continue;
                        }
                    }
                    // 로딩 실패
                    catch (TimeoutException e) {
                        sb.append("챔피언 목록 접속 - 429 error");
                        System.out.println(sb);
                        index--;
                        continue;
                    }

                }
                // 현재 티어가 없음
                catch (TimeoutException e) {
                    sb.append("현재 티어 - 없음");
                    System.out.println(sb);
                    playerPrevSoloRank.setTier(null);
                    playerPrevSoloRank.setRankNum(null);
                    playerPrevSoloRank.setMostDatas(null);
                }
                // 티어를 못 읽음 왜??? 도대체
                catch (NoSuchElementException e) {
                    sb.append("현재 티어 - xpath 못 찾음");
                    System.out.println(sb);
                    index--;
                    continue;
                }
            }
            // 로딩 실패 or 없는 계정
            catch (TimeoutException e) {

                // 없는 계정
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/header/div[2]/a")));
                    sb.append("최초 접속 - 없는 사용자입니다");
                    System.out.println(sb);
                    playerPrevSoloRank.setTier(null);
                    playerPrevSoloRank.setRankNum(null);
                    playerPrevSoloRank.setMostDatas(null);
                }
                // 로딩 실패
                catch (TimeoutException e2) {
                    sb.append("최초 접속 - 429 error");
                    System.out.println(sb);
                    index--;
                    continue;
                }
            }
            // 갑자기 알림 이슈
            catch (UnhandledAlertException e) {
                sb.append("최초 접속 - 예상치 못한 알림 이슈");
                System.out.println(sb);
                index--;
                continue;
            }
            playerPrevSoloRanks.add(playerPrevSoloRank);
        }

        driver.quit();
        playerPrevSoloRankRepository.saveAll(playerPrevSoloRanks);
    }
}