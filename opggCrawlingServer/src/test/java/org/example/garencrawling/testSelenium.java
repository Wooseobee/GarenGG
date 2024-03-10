package org.example.garencrawling;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.garencrawling.mostchampion.domain.MostData;
import org.example.garencrawling.mostchampion.domain.PlayerCurSoloRank;
import org.example.garencrawling.mostchampion.domain.PlayerInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class testSelenium {

    public static List<PlayerInfo> playerInfos = new ArrayList<>();

    public static String[] proxyAddress = new String[]{
            "52.79.226.142:3128",
            "15.164.142.18:3128",
            "13.209.64.11:3128",
    };

    public static int sleepTime = 5000;

    public static void main(String[] args) throws InterruptedException {

        playerInfos.add(new PlayerInfo(0, "", "", "진뚱이용-KR1"));
        playerInfos.add(new PlayerInfo(0, "", "", "Hide on bush-KR1"));
        playerInfos.add(new PlayerInfo(0, "", "", "와진짜오랜만이다-머쓱타드"));
        playerInfos.add(new PlayerInfo(0, "", "", "야 스 오-GG48"));
        playerInfos.add(new PlayerInfo(0, "", "", "교통약자석VVIP-2943"));
        playerInfos.add(new PlayerInfo(0, "", "", "297 044-KR1"));
        playerInfos.add(new PlayerInfo(0, "", "", "신사 나르-2530"));


        ////////////////////////////////////////////////////////////////////////////////////////////

        WebElement element;
        ChromeOptions options;
        ChromeDriver driver;
        WebDriverWait wait;
        List<WebElement> rows;
        ArrayList<PlayerCurSoloRank> playerCurSoloRanks = new ArrayList<>();

        ////////////////////////////////////////////////////////////////////////////////////////////

        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();

//        Proxy 관련
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyAddress[0])
                .setSslProxy(proxyAddress[0]);
        options.setProxy(proxy);

//        options.addArguments("--headless"); // headless 모드 활성화
        options.addArguments("--disable-gpu"); // 최신 버전에서는 필요하지 않지만 호환성을 위해 추천
        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 명시적 대기 시간 설정

        ////////////////////////////////////////////////////////////////////////////////////////////

        for (int index = 0; index < playerInfos.size(); index++) {

            PlayerInfo playerInfo = playerInfos.get(index);

            StringBuilder sb = new StringBuilder();

            sb.append("playerInfo.getPlayerId() = ").append(playerInfo.getPlayerId()).append(" ");
            sb.append("playerInfo.getUserNickname() = ").append(playerInfo.getUserNickname()).append(" ");

            PlayerCurSoloRank playerCurSoloRank = new PlayerCurSoloRank();

            // playerId
            playerCurSoloRank.setPlayerId(playerInfo.getPlayerId());

            // tier rankNum
            driver.get("https://www.op.gg/summoners/kr/" + playerInfo.getUserNickname());
            try {
                // 게임 중이여도 뭐 오케이
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[7]/div[1]/div[1]")));

                try {
                    // 티어
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("tier")));

                    String str = driver.findElement(By.className("tier")).getText();
                    if (str == null || str.isEmpty()) {
                        sb.append("현재 티어 - str 이상함");
                    } else {

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
                                    playerCurSoloRank.getMostDatas().add(mostData);
                                }
                                sb.append("----------------------성공");
                                playerCurSoloRanks.add(playerCurSoloRank);
                            }
                            // 로딩 실패 or 기록된 전적이 없습니다.
                            catch (TimeoutException e) {

                                // 기록된 전적이 없습니다.
                                try {
                                    wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("/html/body/div[1]/div[6]/div/table/tbody/tr/td/div/div/p"), "기록된 전적이 없습니다."));
                                    sb.append("챔피언 목록 - 기록된 전적이 없습니다.");
                                }
                                // 로딩 실패
                                catch (TimeoutException e2) {
                                    sb.append("챔피언 목록 - 로딩 실패");
                                    Thread.sleep(sleepTime);
                                    index--;
                                }
                            }
                            // 이상한 에러
                            catch (StaleElementReferenceException e) {
                                sb.append("챔피언 목록 - StaleElementReferenceException");
                                index--;
                            }
                        }
                        // 로딩 실패
                        catch (TimeoutException e) {
                            sb.append("챔피언 목록 접속 - 로딩 실패");
                            Thread.sleep(sleepTime);
                            index--;
                        }
                        // 버튼 xpath 못 찾음
                        catch (NoSuchElementException e) {
                            sb.append("챔피언 목록 접속 - 버튼 xpath 못 찾음");
                            index--;
                        }
                    }
                }
                // 현재 티어가 없음
                catch (TimeoutException e) {
                    sb.append("현재 티어 - 없음");
                }
                // 티어
                catch (NoSuchElementException e) {
                    sb.append("현재 티어 - className 못 찾음");
                    index--;
                }
            }
            // 없는 계정 or 로딩 실패
            catch (TimeoutException e) {
                // 없는 계정
                try {
                    wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("/html/body/div[1]/div[3]/div/h2"),
                            "OP.GG에 등록되지 않은 소환사입니다. 오타를 확인 후 다시 검색해주세요."));
                    sb.append("최초 접속 - 없는 사용자입니다");
                }
                // 로딩 실패
                catch (TimeoutException e2) {
                    sb.append("최초 접속 - 로딩 실패");
                    Thread.sleep(sleepTime);
                    index--;
                }
            }
            // 갑자기 알림 이슈
            catch (UnhandledAlertException e) {
                sb.append("최초 접속 - 알림 이슈");
                index--;
            }
            System.out.println(sb);
        }

        driver.quit();

    }
}
