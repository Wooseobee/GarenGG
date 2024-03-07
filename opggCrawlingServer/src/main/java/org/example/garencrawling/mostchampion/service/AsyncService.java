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

    int saveSize = 20;

    @Async("threadPoolTaskExecutor")
    public void processPlayersInRange(int startPlayerId, int endPlayerId, List<PlayerInfo> savedPlayerInfos, int threadNumber) {

        int currentStartPlayerId = startPlayerId;
        while (currentStartPlayerId <= endPlayerId) {
            int currentEndPlayerId = Math.min(currentStartPlayerId + saveSize - 1, endPlayerId);

//            List<PlayerPrevSoloRank> savedPlayerPrevSoloRanks = playerPrevSoloRankRepository.findAll();

            List<PlayerInfo> tmpPlayerInfos = new ArrayList<>();
            for (int i = currentStartPlayerId; i <= currentEndPlayerId; i++) {

                PlayerInfo currentSavedPlayerInfo = savedPlayerInfos.get(i - 1);

                boolean exist = false;
//                for (PlayerPrevSoloRank savedPlayerPrevSoloRank : savedPlayerPrevSoloRanks) {
//                    if (savedPlayerPrevSoloRank.getPlayerId() == currentSavedPlayerInfo.getPlayerId()) {
//
//                        System.out.println("savedPlayerPrevSoloRank.getPlayerId() = " + savedPlayerPrevSoloRank.getPlayerId());
//                        System.out.println("savedPlayerPrevSoloRank.getTier() = " + savedPlayerPrevSoloRank.getTier());
//                        System.out.println("savedPlayerPrevSoloRank.getRankNum() = " + savedPlayerPrevSoloRank.getRankNum());
//                        System.out.println("savedPlayerPrevSoloRank.getMostDatas().size() = " + savedPlayerPrevSoloRank.getMostDatas().size());
//
//                        if (savedPlayerPrevSoloRank.getTier() != null) {
//                            if (savedPlayerPrevSoloRank.getMostDatas() != null) {
//                                if (!savedPlayerPrevSoloRank.getMostDatas().isEmpty())
//                                    exist = true;
//                            }
//                        }
//                    }
//                }
                if (!exist) {
                    tmpPlayerInfos.add(currentSavedPlayerInfo);
                }
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

        String[] proxyAddress = new String[]{
                "121.139.218.165:43295",
                "211.234.125.5:443",
                "210.95.145.226:3128",
                "211.234.125.3:443",
                "210.95.145.226:3128",
                "152.99.145.25:80"
        };
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyAddress[proxyAddress.length % threadNumber])
                .setSslProxy(proxyAddress[proxyAddress.length % threadNumber]);

        ////////////////////////////////////////////////////////////////////////////////////////////

        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();

        options.setProxy(proxy);
        options.addArguments("--headless"); // headless 모드 활성화
        options.addArguments("--disable-gpu"); // 최신 버전에서는 필요하지 않지만 호환성을 위해 추천
        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 명시적 대기 시간 설정

        ////////////////////////////////////////////////////////////////////////////////////////////

        for (int index = 0; index < playerInfos.size(); index++) {
            StringBuilder sb = new StringBuilder();
            PlayerInfo playerInfo = playerInfos.get(index);

            PlayerPrevSoloRank playerPrevSoloRank = new PlayerPrevSoloRank();
            playerPrevSoloRank.setPlayerId(playerInfo.getPlayerId());
//            System.out.print("playerInfo.getPlayerId() = " + playerInfo.getPlayerId() + " ");
            sb.append("playerInfo.getPlayerId() = ").append(playerInfo.getPlayerId()).append(" ");
//            System.out.print("playerInfo.getUserNickname() = " + playerInfo.getUserNickname() + " ");
            sb.append("playerInfo.getUserNickname() = ").append(playerInfo.getUserNickname()).append(" ");

            driver.get("https://www.op.gg/summoners/kr/" + playerInfo.getUserNickname() + "/champions");

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
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[4]/div[1]/div/div[1]/div[2]/div[1]/div/ul/li[1]/div")));
                    StringTokenizer st = new StringTokenizer(driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/div[1]/div[2]/div[1]/div/ul/li[1]/div")).getText());

                    String season1 = st.nextToken(); // S2023
                    if (!season1.equals("S2023")) {
                        throw new MyException("시즌이 S2023이 아닙니다");
                    }

                    String season2 = st.nextToken(); // S2
                    if (!season2.equals("S2")) {
                        throw new MyException("시즌이 S2가 아닙니다");
                    }

                    String tier = st.nextToken();
                    playerPrevSoloRank.setTier(tier);

                    if (tier.equals("Master") || tier.equals("Grandmaster") || tier.equals("Challenger")) {

                    } else {
                        playerPrevSoloRank.setRankNum(st.nextToken());
                    }

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

//                        System.out.println("성공");
                        sb.append("성공");
                        System.out.println(sb);
                    }
                    // 로딩 실패
                    catch (TimeoutException e) {
//                        System.out.println("429 error - 챔피언 목록");
                        sb.append("429 error - 챔피언 목록");
                        System.out.println(sb);
                        index--;
                        continue;
                    }
                    // 이상한 에러
                    catch (StaleElementReferenceException e) {
                        sb.append("StaleElementReferenceException - 챔피언 목록");
                        System.out.println(sb);
                        index--;
                        continue;
                    }
                }
                // 로딩 실패 or 티어 목록이 없음
                catch (TimeoutException e) {
                    // 이전 티어가 없음
                    try {
                        List<WebElement> liElements = driver.findElements(By.cssSelector("ul.tier-list > li"));
                        if (liElements.isEmpty()) {
//                            System.out.println("이전 티어가 없습니다");
                            sb.append("이전 티어가 없습니다");
                            System.out.println(sb);
                            playerPrevSoloRank.setTier(null);
                            playerPrevSoloRank.setRankNum(null);
                            playerPrevSoloRank.setMostDatas(null);
                        }
                    }
                    // 로딩 실패
                    catch (NoSuchElementException e2) {
//                        System.out.println("429 error - 티어 목록");
                        sb.append("429 error - 티어 목록");
                        System.out.println(sb);
                        index--;
                        continue;
                    }
                }
                // 시즌이 틀림
                catch (MyException e) {
//                    System.out.println(e.getMessage());
                    sb.append(e.getMessage());
                    System.out.println(sb);
                    playerPrevSoloRank.setTier(null);
                    playerPrevSoloRank.setRankNum(null);
                    playerPrevSoloRank.setMostDatas(null);
                }

            }
            // 로딩 실패 or 없는 계정
            catch (TimeoutException e) {

                // 없는 계정
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/header/div[2]/a/img")));
//                    System.out.println("없는 사용자 입니다");
                    sb.append("없는 사용자 입니다");
                    System.out.println(sb);
                    playerPrevSoloRank.setTier(null);
                    playerPrevSoloRank.setRankNum(null);
                    playerPrevSoloRank.setMostDatas(null);
                }
                // 로딩 실패
                catch (TimeoutException e2) {
//                    System.out.println("429 error - 최초 접속");
                    sb.append("429 error - 최초 접속");
                    System.out.println(sb);
                    index--;
                    continue;
                }
            }

            playerPrevSoloRanks.add(playerPrevSoloRank);
        }

        driver.quit();
        playerPrevSoloRankRepository.saveAll(playerPrevSoloRanks);
    }
}