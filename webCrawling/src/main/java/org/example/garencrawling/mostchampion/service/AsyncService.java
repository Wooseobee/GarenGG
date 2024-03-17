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

            sb = new StringBuilder();
            sb.append(crawling(subFindedPlayerInfos.subList(currentStartIndex, currentEndIndex + 1), threadNumber)).append(" ");
            sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" threadNumber = ").append(threadNumber).append(" ").append((currentEndIndex + 1) * 100 / (endIndex + 1)).append("% 완료");
            System.out.println(sb);

            currentStartIndex = currentEndIndex + 1;
        }

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" threadNumber = ").append(threadNumber).append(" 종료");
        System.out.println(sb);
    }

    public int crawling(List<PlayerInfo> playerInfos, int threadNumber) throws InterruptedException {

        ArrayList<PlayerCurSoloRank> playerCurSoloRanks = new ArrayList<>();

        for (int index = 0; index < playerInfos.size(); index++) {
            try {
                WebElement webElement;
                List<WebElement> webElements;
                String gotText;
                String timeData;

                PlayerCurSoloRank playerCurSoloRank = new PlayerCurSoloRank();
                playerCurSoloRank.setPlayerId(playerInfos.get(index).getPlayerId());

                GlobalConstants.drivers.get(threadNumber - 1).get("https://fow.kr/find/" + playerInfos.get(index).getUserNickname());

                // 서치 컨테이너
                try {
                    GlobalConstants.waits.get(threadNumber - 1).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#searchContainer")));
                } catch (TimeoutException e) {
                    index--;
                    continue;
                }

                // 사용자 정보
                try {
                    gotText = GlobalConstants.waits.get(threadNumber - 1).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.topp > div.profile > div:nth-child(2)"))).getText();
                } catch (TimeoutException e) {
                    continue;
                }

                try {
                    // 갱신 할지 말지
                    timeData = gotText.split(" ")[gotText.split(" ").length - 2];
                    if (timeData.substring(timeData.length() - 1).equals("일") && Integer.parseInt(timeData.substring(0, timeData.length() - 1)) > 3) {
                        webElement = GlobalConstants.waits.get(threadNumber - 1).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.topp > div.profile > div:nth-child(2) > div")));
                        GlobalConstants.drivers.get(threadNumber - 1).executeScript("arguments[0].click();", webElement);

                        GlobalConstants.waits.get(threadNumber - 1).until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.topp > div.profile > div:nth-child(2) > div"), "갱신불가"));
                    }

                    // 현재 솔로 랭크 티어
                    gotText = GlobalConstants.waits.get(threadNumber - 1).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.table_summary > div:nth-child(2) > div:nth-child(2) > b > font"))).getText();
                    String tier = gotText.split(" ")[0];
                    if (tier.equals("배치")) {
                        continue;
                    }
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
                } catch (Exception e) {
                    index--;
                    continue;
                }

                try {
                    // 솔로 랭크 버튼
                    webElement = GlobalConstants.waits.get(threadNumber - 1).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.rankchamp_S14A_menu > a.sbtn.rankchamp_list.rankchamp_list_solo")));
                    GlobalConstants.drivers.get(threadNumber - 1).executeScript("arguments[0].click();", webElement);

                    // 챔피언 목록
                    webElements = GlobalConstants.drivers.get(threadNumber - 1).findElements(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.rankchamp_S14A_div.rankchamp_S14A_div_solo > table > tbody > tr"));
                } catch (TimeoutException e) {
                    index--;
                    continue;
                }

                try {
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
                    index--;
                    continue;
                }

            } catch (Exception e) {
                GlobalConstants.drivers.get(threadNumber - 1).quit();
                GlobalConstants.drivers.set(threadNumber - 1, new ChromeDriver(GlobalConstants.options));
                GlobalConstants.waits.set(threadNumber - 1, new WebDriverWait(GlobalConstants.drivers.get(threadNumber - 1), Duration.ofSeconds(GlobalConstants.waitTime)));
                index--;
            }
        }
        playerCurSoloRankRepository.saveAll(playerCurSoloRanks);
        return playerCurSoloRanks.size();
    }
}