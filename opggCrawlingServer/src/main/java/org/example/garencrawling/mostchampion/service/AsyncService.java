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

        if ((threadNumber - 1) % GlobalConstants.proxyAddress.length != 0) {
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(GlobalConstants.proxyAddress[(threadNumber - 1) % GlobalConstants.proxyAddress.length])
                    .setSslProxy(GlobalConstants.proxyAddress[(threadNumber - 1) % GlobalConstants.proxyAddress.length]);
            options.setProxy(proxy);
        }

        options.addArguments("--headless"); // headless 모드 활성화
        options.addArguments("--disable-gpu"); // 최신 버전에서는 필요하지 않지만 호환성을 위해 추천
        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 명시적 대기 시간 설정

        ////////////////////////////////////////////////////////////////////////////////////////////

        for (int index = 0; index < playerInfos.size(); index++) {
            StringBuilder sb = new StringBuilder();

            PlayerInfo playerInfo = playerInfos.get(index);

            sb.append("threadNumber = ").append(threadNumber).append(" ");
            sb.append("playerInfo.getPlayerId() = ").append(playerInfo.getPlayerId()).append(" ");
            sb.append("playerInfo.getUserNickname() = ").append(playerInfo.getUserNickname()).append(" ");

            PlayerCurSoloRank playerCurSoloRank = new PlayerCurSoloRank();

            // playerId
            playerCurSoloRank.setPlayerId(playerInfo.getPlayerId());

            // opgg
            if (threadNumber <= GlobalConstants.proxyAddress.length) {

                // tier rankNum
                driver.get("https://www.op.gg/summoners/kr/" + playerInfo.getUserNickname());
                try {
                    // 게임 중이여도 뭐 오케이
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".css-1kw4425.ecc8cxr0")));

                    try {
                        // 티어
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".tier")));
                        String str = driver.findElement(By.cssSelector(".tier")).getText();

                        StringTokenizer st = new StringTokenizer(str);
                        String tier = st.nextToken();
                        playerCurSoloRank.setTier(tier);
                        if (tier.equals("Master") || tier.equals("Grandmaster") || tier.equals("Challenger")) {

                        } else {
                            playerCurSoloRank.setRankNum(st.nextToken());
                        }

                        try {
                            // 챔피언
                            driver.get("https://www.op.gg/summoners/kr/" + playerInfo.getUserNickname() + "/champions");

                            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".css-myjiba.eggkc9d0")));
                            element = driver.findElement(By.cssSelector(".css-myjiba.eggkc9d0"));
                            driver.executeScript("arguments[0].click();", element);

                            try {

                                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".rank.css-116yacx.eyczova1")));
                                rows = driver.findElements(By.cssSelector(".css-npdgn9.emhbfki4 tbody tr"));

                                for (WebElement row : rows) {
                                    List<WebElement> cols = row.findElements(By.tagName("td"));

                                    MostData mostData = new MostData();

                                    for (int i = 0; i < cols.size(); i++) {
                                        WebElement col = cols.get(i);
                                        String tmp = col.getText();

                                        if (i == 0)
                                            mostData.setMostSeq(tmp);
                                        else if (i == 1) {
                                            if (GlobalConstants.championNames.get(tmp) == null) {
                                                sb.append(tmp).append(" ");
                                            }
                                            mostData.setChampion(GlobalConstants.championNames.get(tmp));
                                        } else if (i == 2) {
                                            tmp = tmp.replace("\n", "").replace(" ", "");
                                            tmp.replace("승", "W");
                                            tmp.replace("패", "L");
                                            mostData.setGame(tmp);
                                        }

                                    }
                                    playerCurSoloRank.getMostDatas().add(mostData);
                                }
                                sb.append("----------------------성공");
                                playerCurSoloRanks.add(playerCurSoloRank);
                            } catch (Exception e) {
                                sb.append("챔피언 목록 - Exception");
                                Thread.sleep(GlobalConstants.sleepTime);
                            }
                        } catch (Exception e) {
                            sb.append("챔피언 목록 접속 - Exception");
                            Thread.sleep(GlobalConstants.sleepTime);
                        }

                    } catch (Exception e) {
                        sb.append("현재 티어 - Exception");
                        Thread.sleep(GlobalConstants.sleepTime);
                    }
                } catch (Exception e) {
                    sb.append("최초 접속 - Exception");
                    Thread.sleep(GlobalConstants.sleepTime);
                }
            }

            // fowkr
            else {
                // tier rankNum
                driver.get("https://fow.kr/find/" + playerInfo.getUserNickname());

                try {
                    // 게임 중이여도 뭐 오케이
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table_summary")));

                    try {
                        // 티어
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[5]/div[1]/div[1]/div[5]/div[1]/div[2]/b")));
                        String line = driver.findElement(By.xpath("/html/body/div[5]/div[1]/div[1]/div[5]/div[1]/div[2]/b")).getText();
                        StringTokenizer st = new StringTokenizer(line);

                        String tier = st.nextToken();

                        if (tier.equals("배치")) {
                            sb.append("현재 티어 - 없습니다");
                            Thread.sleep(GlobalConstants.sleepTime);
                        } else {

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

                            try {
                                // 챔피언
                                element = driver.findElement(By.cssSelector(".sbtn.rankchamp_list.rankchamp_list_solo"));
                                driver.executeScript("arguments[0].click();", element);

                                try {
                                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[5]/div[1]/div[1]/div[8]/table/tbody/tr")));
                                    rows = driver.findElements(By.xpath("/html/body/div[5]/div[1]/div[1]/div[8]/table/tbody/tr"));

                                    int mostSeq = 1;
                                    for (WebElement row : rows) {
                                        List<WebElement> cols = row.findElements(By.tagName("td"));

                                        MostData mostData = new MostData();
                                        mostData.setMostSeq(String.valueOf(mostSeq));
                                        mostSeq++;

                                        for (int i = 0; i < cols.size(); i++) {
                                            WebElement col = cols.get(i);
                                            String tmp = col.getText();

                                            if (i == 0) {

                                                if (GlobalConstants.championNames.get(tmp) == null) {
                                                    sb.append(tmp).append(" ");
                                                }
                                                mostData.setChampion(GlobalConstants.championNames.get(tmp));
                                            } else if (i == 1) {
                                                mostData.setGame(tmp);
                                            } else if (i == 2) {
                                                double percentage = Double.parseDouble(tmp.substring(0, tmp.length() - 1));
                                                int win = (int) (Integer.parseInt(mostData.getGame()) * percentage) / 100;
                                                int lose = Integer.parseInt(mostData.getGame()) - win;

                                                String result = "";
                                                if (win != 0)
                                                    result = result + String.valueOf(win) + "W";
                                                if (lose != 0)
                                                    result = result + String.valueOf(lose) + "L";
                                                result = result + String.valueOf((int) percentage) + "%";

                                                mostData.setGame(result);
                                            }
                                        }
                                        playerCurSoloRank.getMostDatas().add(mostData);
                                    }
                                    sb.append("----------------------성공");
                                    playerCurSoloRanks.add(playerCurSoloRank);
                                } catch (Exception e) {
                                    sb.append("챔피언 목록 - Exception");
                                    Thread.sleep(GlobalConstants.sleepTime);
                                }
                            } catch (Exception e) {
                                sb.append("솔로 랭크 버튼 - Exception");
                                Thread.sleep(GlobalConstants.sleepTime);
                            }
                        }
                    } catch (Exception e) {
                        sb.append("현재 티어 - Exception");
                        Thread.sleep(GlobalConstants.sleepTime);
                    }
                } catch (Exception e) {
                    sb.append("최초 접속 - Exception");
                    Thread.sleep(GlobalConstants.sleepTime);
                }
            }
            System.out.println(sb);
        }

        driver.quit();
        playerCurSoloRankRepository.saveAll(playerCurSoloRanks);
    }
}