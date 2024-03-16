package org.example.garencrawling.mostchampion.service;

import lombok.RequiredArgsConstructor;
import org.example.garencrawling.global.GlobalConstants;
import org.example.garencrawling.mostchampion.domain.MostData;
import org.example.garencrawling.mostchampion.domain.PlayerCurSoloRank;
import org.example.garencrawling.mostchampion.domain.PlayerInfo;
import org.example.garencrawling.mostchampion.repository.PlayerCurSoloRankRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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
        StringBuilder sb;

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" threadNumber = ").append(threadNumber).append(" 시작");
        System.out.println(sb);

        int currentStartIndex = 0;
        int endIndex = subFindedPlayerInfos.size() - 1;

        while (currentStartIndex <= endIndex) {
            int currentEndIndex = Math.min(currentStartIndex + GlobalConstants.saveSize - 1, endIndex);

            crawling(subFindedPlayerInfos.subList(currentStartIndex, currentEndIndex + 1));

            sb = new StringBuilder();
            sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" threadNumber = ").append(threadNumber).append(" ").append((currentEndIndex + 1) * 100 / (endIndex + 1)).append("% 완료");
            System.out.println(sb);

            currentStartIndex = currentEndIndex + 1;
        }

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" threadNumber = ").append(threadNumber).append(" 종료");
        System.out.println(sb);
    }

    public void crawling(List<PlayerInfo> playerInfos) throws InterruptedException {

        ArrayList<PlayerCurSoloRank> playerCurSoloRanks = new ArrayList<>();

        ChromeDriver driver = new ChromeDriver(GlobalConstants.options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.waitTime));

        for (int index = 0; index < playerInfos.size(); index++) {
            try {
                WebElement webElement;
                List<WebElement> webElements;
                String gotText;
                String timeData;

                PlayerCurSoloRank playerCurSoloRank = new PlayerCurSoloRank();
                playerCurSoloRank.setPlayerId(playerInfos.get(index).getPlayerId());

                driver.get("https://fow.kr/find/" + playerInfos.get(index).getUserNickname());

                // 서치 컨테이너
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#searchContainer")));

                // 사용자 정보
                try {
                    gotText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.topp > div.profile > div:nth-child(2)"))).getText();
                } catch (Exception e) {
                    continue;
                }

                // 갱신 할지 말지
                timeData = gotText.split(" ")[gotText.split(" ").length - 2];
                if (timeData.charAt(timeData.length() - 1) == '일' && Integer.parseInt(timeData.substring(0, timeData.length() - 1)) > 7) {
                    webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.topp > div.profile > div:nth-child(2) > div")));
                    driver.executeScript("arguments[0].click();", webElement);
                    index--;
                    continue;
                }

                // 현재 솔로 랭크 티어
                gotText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.table_summary > div:nth-child(2) > div:nth-child(2) > b > font"))).getText();
                String tier = gotText.split(" ")[0];
                tier = tier.toLowerCase();
                tier = Character.toUpperCase(tier.charAt(0)) + tier.substring(1);
                playerCurSoloRank.setTier(tier);

                if (!(tier.equals("Master") || tier.equals("Grandmaster") || tier.equals("Challenger"))) {
                    String rankNum = gotText.split(" ")[1];
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

                // 솔로 랭크 버튼
                webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.rankchamp_S14A_menu > a.sbtn.rankchamp_list.rankchamp_list_solo")));
                driver.executeScript("arguments[0].click();", webElement);

                // 챔피언 목록
                webElements = driver.findElements(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.rankchamp_S14A_div.rankchamp_S14A_div_solo > table > tbody > tr"));

                playerCurSoloRank.setMostDatas(new ArrayList<>());
                int mostSeq = 1;
                for (WebElement row : webElements) {
                    MostData mostData = new MostData();
                    mostData.setMostSeq(String.valueOf(mostSeq));
                    mostSeq++;

                    List<WebElement> cols = row.findElements(By.tagName("td"));
                    for (int i = 0; i < cols.size(); i++) {
                        String tmp = cols.get(i).getText();

                        if (i == 0) {
                            mostData.setChampion(GlobalConstants.championNames.get(tmp));
                        } else if (i == 1) {
                            mostData.setGame(tmp);
                        } else if (i == 2) {
                            double percentage = Double.parseDouble(tmp.substring(0, tmp.length() - 1));
                            int win = (int) (Integer.parseInt(mostData.getGame()) * percentage / 100 + 0.99);
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
                playerCurSoloRanks.add(playerCurSoloRank);
            } catch (Exception e) {
                driver.quit();
                driver = new ChromeDriver(GlobalConstants.options);
                wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.waitTime));
                index--;
            }
        }

        playerCurSoloRankRepository.saveAll(playerCurSoloRanks);
        System.out.println("playerCurSoloRanks.size() = " + playerCurSoloRanks.size());
        driver.quit();

    }
}