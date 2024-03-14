package org.example.garencrawling.mostchampion.service;

import lombok.RequiredArgsConstructor;
import org.example.garencrawling.global.GlobalConstants;
import org.example.garencrawling.mostchampion.domain.MostData;
import org.example.garencrawling.mostchampion.domain.PlayerCurSoloRank;
import org.example.garencrawling.mostchampion.domain.PlayerInfo;
import org.example.garencrawling.mostchampion.repository.PlayerCurSoloRankRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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
        StringBuilder sb;

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" threadNumber = ").append(threadNumber).append(" 시작");
        System.out.println(sb);

        int currentStartIndex = 0;
        int endIndex = subFindedPlayerInfos.size() - 1;

        while (currentStartIndex <= endIndex) {
            int currentEndIndex = Math.min(currentStartIndex + GlobalConstants.saveSize - 1, endIndex);

            crawling(subFindedPlayerInfos.subList(currentStartIndex, currentEndIndex + 1), threadNumber);

            sb = new StringBuilder();
            sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" threadNumber = ").append(threadNumber).append(" ").append((currentEndIndex + 1) * 100 / (endIndex + 1)).append("% 완료");
            System.out.println(sb);

            currentStartIndex = currentEndIndex + 1;
        }

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" threadNumber = ").append(threadNumber).append(" 종료");
        System.out.println(sb);

        GlobalConstants.drivers.get(threadNumber - 1).quit();
    }

    public void crawling(List<PlayerInfo> playerInfos, int threadNumber) throws InterruptedException {

        ArrayList<PlayerCurSoloRank> playerCurSoloRanks = new ArrayList<>();
        WebElement element;
        List<WebElement> rows;
        int tryCount;
        String getText = null;

        try {

            for (int index = 0; index < playerInfos.size(); index++) {

                PlayerInfo playerInfo = playerInfos.get(index);

                PlayerCurSoloRank playerCurSoloRank = new PlayerCurSoloRank();
                playerCurSoloRank.setPlayerId(playerInfo.getPlayerId());

                GlobalConstants.drivers.get(threadNumber - 1).get("https://fow.kr/find/" + playerInfo.getUserNickname());

                // 닉네임
                tryCount = 1;
                while (tryCount <= GlobalConstants.tryMaxCount) {
                    try {
                        getText = GlobalConstants.waits.get(threadNumber - 1).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.topp > div.profile > div:nth-child(2) > span.username"))).getText();
                        if (!getText.isEmpty()) {
                            break;
                        }
                    } catch (Exception e) {
                    }
                    GlobalConstants.drivers.get(threadNumber - 1).navigate().refresh();
                    tryCount++;
                }
                if (tryCount > GlobalConstants.tryMaxCount) {
                    // System.out.println("닉네임 초과");
                    continue;
                }

//                // 갱신 가능 버튼
//                tryCount = 1;
//                while (tryCount <= GlobalConstants.tryMaxCount) {
//                    try {
//                        element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.topp > div.profile > div:nth-child(2) > div")));
//                        getText = element.getText();
//                        System.out.println("getText1 = " + getText);
//                        if (getText.equals("갱신 가능")) {
//                            driver.executeScript("arguments[0].click();", element);
//
//                            while (true) {
//                                try {
//                                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.topp > div.profile > div:nth-child(2) > div")));
//                                    getText = element.getText();
//                                    System.out.println("getText2 = " + getText);
//                                    if (getText.equals("갱신불가")) {
//                                        break;
//                                    }
//                                } catch (Exception e) {
//                                }
//                                driver.navigate().refresh();
//                            }
//
//                            break;
//                        } else if (getText.equals("갱신불가")) {
//                            break;
//                        }
//                    } catch (Exception e) {
//                    }
//                    driver.navigate().refresh();
//                    tryCount++;
//                }
//                if (tryCount > GlobalConstants.tryMaxCount) {
//                     System.out.println("갱신 버튼 초과");
//                    continue;
//                }

                // 현재 티어
                tryCount = 1;
                while (tryCount <= GlobalConstants.tryMaxCount) {
                    try {
                        getText = GlobalConstants.waits.get(threadNumber - 1).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.table_summary > div:nth-child(2) > div:nth-child(2) > b > font"))).getText();
                        if (!getText.isEmpty()) {
                            break;
                        }
                    } catch (Exception e) {
                    }
                    GlobalConstants.drivers.get(threadNumber - 1).navigate().refresh();
                    tryCount++;
                }
                if (tryCount > GlobalConstants.tryMaxCount) {
                    // System.out.println("현재 티어 초과");
                    continue;
                }

                try {
                    String tier = getText.split(" ")[0];
                    tier = tier.toLowerCase();
                    tier = Character.toUpperCase(tier.charAt(0)) + tier.substring(1);
                    playerCurSoloRank.setTier(tier);

                    if (!(tier.equals("Master") || tier.equals("Grandmaster") || tier.equals("Challenger"))) {
                        String rankNum = getText.split(" ")[1];
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
                    // System.out.println("현재 티어 읽기 실패");
                    continue;
                }

                // 솔로 랭크 버튼
                tryCount = 1;
                while (tryCount <= GlobalConstants.tryMaxCount) {
                    try {
                        element = GlobalConstants.waits.get(threadNumber - 1).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.rankchamp_S14A_menu > a.sbtn.rankchamp_list.rankchamp_list_solo")));
                        GlobalConstants.drivers.get(threadNumber - 1).executeScript("arguments[0].click();", element);
                        break;
                    } catch (Exception e) {

                    }
                    GlobalConstants.drivers.get(threadNumber - 1).navigate().refresh();
                    tryCount++;
                }
                if (tryCount > GlobalConstants.tryMaxCount) {
                    // System.out.println("솔로 랭크 버튼 초과");
                    continue;
                }

                // 챔피언 목록
                tryCount = 1;
                while (tryCount <= GlobalConstants.tryMaxCount) {
                    try {
                        GlobalConstants.waits.get(threadNumber - 1).until(new ExpectedCondition<Boolean>() {
                            public Boolean apply(WebDriver driver) {
                                List<WebElement> elements = driver.findElements(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.rankchamp_S14A_div.rankchamp_S14A_div_solo > table > tbody > tr"));
                                for (WebElement element : elements) {
                                    if (!element.isDisplayed()) {
                                        return false;
                                    }
                                }
                                return true;
                            }
                        });
                        rows = GlobalConstants.drivers.get(threadNumber - 1).findElements(By.cssSelector("#content-container > div:nth-child(1) > div:nth-child(2) > div.rankchamp_S14A_div.rankchamp_S14A_div_solo > table > tbody > tr"));

                        int mostSeq = 1;
                        playerCurSoloRank.setMostDatas(new ArrayList<>());

                        for (WebElement row : rows) {
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
                        playerCurSoloRanks.add(playerCurSoloRank);
                        // System.out.println("성공");
                        break;
                    } catch (Exception e) {

                    }
                    tryCount++;
                }
                if (tryCount > GlobalConstants.tryMaxCount) {
                    // System.out.println("챔피언 목록 초과");
                    continue;
                }

            }
        } catch (Exception e) {
//             System.out.println("예상치 못한 오류 발생!!");
            if (GlobalConstants.drivers.get(threadNumber - 1) != null) {
                GlobalConstants.drivers.get(threadNumber - 1).quit();
            }
            GlobalConstants.drivers.set(threadNumber - 1, new ChromeDriver(GlobalConstants.options));
            GlobalConstants.waits.set(threadNumber - 1, new WebDriverWait(GlobalConstants.drivers.get(threadNumber - 1), Duration.ofSeconds(GlobalConstants.waitTime)));
        }

        playerCurSoloRankRepository.saveAll(playerCurSoloRanks);

    }
}