package org.example.garencrawling.mostchampion.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.example.garencrawling.mostchampion.domain.MostData;
import org.example.garencrawling.mostchampion.domain.PlayerInfo;
import org.example.garencrawling.mostchampion.domain.PlayerPrevSoloRank;
import org.example.garencrawling.mostchampion.repository.PlayerInfoRepository;
import org.example.garencrawling.mostchampion.repository.PlayerPrevSoloRankRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@EnableMongoRepositories(basePackageClasses = {PlayerPrevSoloRankRepository.class})

public class AsyncService {

    private final PlayerPrevSoloRankRepository playerPrevSoloRankRepository;
    private final PlayerInfoRepository playerInfoRepository;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Void> processPlayersInRange(long startPlayerId, long endPlayerId) {

        List<PlayerInfo> playerInfos = playerInfoRepository.findByPlayerIdBetween(startPlayerId, endPlayerId);
        for (PlayerInfo playerInfo : playerInfos) {
            String userNickname = playerInfo.getSummonerName() + "-" + playerInfo.getTagLine();
            playerInfo.setUserNickname(userNickname);
        }

//        ArrayList<PlayerPrevSoloRank> playerPrevSoloRanks = crawling(playerInfos);

        ArrayList<PlayerPrevSoloRank> allPlayerPrevSoloRanks = new ArrayList<>();

        final int chunkSize = 20;
        for (int i = 0; i < playerInfos.size(); i += chunkSize) {
            // 하위 리스트 생성
            List<PlayerInfo> batch = playerInfos.subList(i, Math.min(playerInfos.size(), i + chunkSize));
            // 하위 리스트에 대해 crawling 호출
            ArrayList<PlayerPrevSoloRank> playerPrevSoloRanks = crawling(batch);
            // 결과를 allPlayerPrevSoloRanks에 추가
            allPlayerPrevSoloRanks.addAll(playerPrevSoloRanks);
        }

        playerPrevSoloRankRepository.saveAll(allPlayerPrevSoloRanks);

        return null;
    }

    public ArrayList<PlayerPrevSoloRank> crawling(List<PlayerInfo> playerInfos) {
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

        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 명시적 대기 시간 설정

        ////////////////////////////////////////////////////////////////////////////////////////////

        for (PlayerInfo playerInfo : playerInfos) {

            PlayerPrevSoloRank playerPrevSoloRank = new PlayerPrevSoloRank();
            playerPrevSoloRank.setPlayerId(playerInfo.getPlayerId());

            try {

                driver.get("https://www.op.gg/summoners/kr/" + playerInfo.getUserNickname() + "/champions");

                // 시즌 읽기
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[4]/div[1]/div/div[1]/div[2]/div[1]/div/ul/li[1]/div/b")));

                StringTokenizer st = new StringTokenizer(driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/div[1]/div[2]/div[1]/div/ul/li[1]/div")).getText());
                st.nextToken(); // S2023
                st.nextToken(); // S2

                playerPrevSoloRank.setTier(st.nextToken());
                if (playerPrevSoloRank.getTier().equals("Master") || playerPrevSoloRank.getTier().equals("Grandmaster") || playerPrevSoloRank.getTier().equals("Challenger")) {

                } else {
                    playerPrevSoloRank.setRankNum(st.nextToken());
                }

                // 챔피언 목록 읽기
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[6]/div/div")));

                element = driver.findElement(By.xpath("/html/body/div[1]/div[6]/div/div/div[1]/div[2]/div/button"));
                driver.executeScript("arguments[0].click();", element);

                element = driver.findElement(By.xpath("/html/body/div[1]/div[6]/div/div/div[1]/div[2]/div/div/button[2]"));
                driver.executeScript("arguments[0].click();", element);

                element = driver.findElement(By.xpath("/html/body/div[1]/div[6]/div/div/div[2]/button[2]"));
                driver.executeScript("arguments[0].click();", element);

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[6]/div/table/tbody/tr[2]")));

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

                System.out.println("playerInfo.getPlayerId() = " + playerInfo.getPlayerId() + " 성공");
            } catch (Exception e) {
                System.out.println("playerInfo.getPlayerId() = " + playerInfo.getPlayerId() + " " + e.getMessage());
                playerPrevSoloRank.setTier(null);
                playerPrevSoloRank.setRankNum(null);
                playerPrevSoloRank.setMostDatas(null);
                MostChampionServiceImpl.failCount++;
            }

            playerPrevSoloRanks.add(playerPrevSoloRank);

        }
        driver.quit();

        return playerPrevSoloRanks;
    }
}
