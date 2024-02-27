package com.example.riotApiCrawling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserRiotApiService {


    public int count;
    public String[] tier = {"IRON", "BRONZE", "SILVER", "GOLD", "PLATINUM", "EMERALD", "DIAMOND"};
    public String[] rank = {"I", "II", "III", "IV"};

    @Value("${riot.api-key}")
    private String apiKey;

    public void test() {
        System.out.println("apiKey : " + apiKey);
    }

    @PostConstruct
    public void crawlUser() throws IOException, InterruptedException {

        System.out.println("crawlUser 실행!!!");
        for (int i = 0; i < tier.length; i++) {
            //티어 하나 유저 목록 불러오기
            for (int j = 0; j < rank.length; j++) {

                //티어하나별 유저 목록 불러오기
                crawlUsersByTier(tier[i], rank[j]);

                //지금까지 불러온 유저 목록에 대한 puuid 저장
//                crawlPUUID();

                //지금까지 불러온 유저목록에대한 tagList 저장.
//                crawltagLine();

                System.out.println(tier[i] + rank[j] + "크롤링 완료!");
            }
        }
    }

    public void crawlUsersByTier(String tier, String rank) throws InterruptedException {
        long startTime = System.currentTimeMillis(); // 시작 시간 측정
        int pageNum = 1;
        while (true) {
            try {
                    URL url = new URL("https://kr.api.riotgames.com/lol/league/v4/entries/RANKED_SOLO_5x5/" + tier + "/" + rank + "?page=" + pageNum + "&api_key=" + apiKey);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // 요청 메서드 설정 (GET 방식)
                    connection.setRequestMethod("GET");

                    // 응답 코드 확인. 입/출력스트림가져오면서 암시적으로 네트워크연결.
                    int responseCode = connection.getResponseCode();
                    System.out.println("Response Code: " + responseCode);

                    // 응답 데이터 읽기
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();

                    // 연결 닫기
                    connection.disconnect();

                    System.out.println(pageNum + ", response : " + response);
                    List<PlayerInfo> playerInfoList = parseJsonResponse(response.toString());

    //            // 플레이어 정보 출력
    //            for (PlayerInfo playerInfo : playerInfoList) {
    //                System.out.println(playerInfo);
    //            }

                System.out.println("playerInfoList.size : "+playerInfoList.size());
                  if (playerInfoList.size() < 205) break; //한번에 200개씩 가져온다.
                pageNum++;

            } catch (IOException e) {
                System.out.println("io exception 발생. 10초 쉬었다갈게요. 현재 페이지 : " + pageNum);
                Thread.sleep(10000);
            }
        }

//        long endTime = System.currentTimeMillis(); // 종료 시간 측정
//        long totalTime = endTime - startTime;
//        System.out.println("Total execution time: " + totalTime + "ms");
    }

    public List<PlayerInfo> parseJsonResponse(String jsonResponse) {
        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, new TypeToken<List<PlayerInfo>>() {
        }.getType());
    }
//    public static int count;
//    public static void crawlUser() throws IOException {
//        // 요청을 보낼 URL 설정
//        URL url = new URL("https://kr.api.riotgames.com/lol/league-exp/v4/entries/RANKED_SOLO_5x5/GOLD/I?page=1&api_key=RGAPI-aec5e563-3790-4932-a2ca-15186040161b");
//        // HttpURLConnection 객체 생성. 네트워크 연결 설정x
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        // 요청 메서드 설정 (GET 방식)
//        connection.setRequestMethod("GET");
//
//        // 응답 코드 확인. 입/출력스트림가져오면서 암시적으로 네트워크연결.
//        int responseCode = connection.getResponseCode();
//        System.out.println("Response Code: " + responseCode);
//
//        // 응답 데이터 읽기
//        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String line;
//        StringBuilder response = new StringBuilder();
//
//        while ((line = reader.readLine()) != null) {
//            response.append(line);
//        }
//
//        reader.close();
//
//        // 응답 출력
//        System.out.println("Response Data:\n" + response.toString());
//
//        // 연결 닫기
//        connection.disconnect();
//
//        /// Gson을 사용하여 JSON 데이터 파싱하여 리스트로 변환
//        List<PlayerInfo> playerInfoList = parseJsonResponse(response.toString());
//
//        /// 플레이어 정보 출력
//        for (PlayerInfo playerInfo : playerInfoList) {
//            System.out.println(playerInfo);
//        }
//        System.out.println("dsize : "+ playerInfoList.size());
//
//    }

//    public static List<PlayerInfo> parseJsonResponse(String jsonResponse) {
//        Gson gson = new Gson();
//        List<PlayerInfo> playerInfoList = gson.fromJson(jsonResponse, new TypeToken<List<PlayerInfo>>(){}.getType());
//        return playerInfoList;
//    }


    // 플레이어 정보 클래스
    static class PlayerInfo {
        String leagueId;
        String queueType;
        String tier;
        String rank;
        String summonerId;
        String summonerName;
        int leaguePoints;
        int wins;
        int losses;
        boolean veteran;
        boolean inactive;
        boolean freshBlood;
        boolean hotStreak;

        @Override
        public String toString() {
            return "PlayerInfo{" + "leagueId='" + leagueId + '\'' + ", queueType='" + queueType + '\'' + ", tier='" + tier + '\'' + ", rank='" + rank + '\'' + ", summonerId='" + summonerId + '\'' + ", summonerName='" + summonerName + '\'' + ", leaguePoints=" + leaguePoints + ", wins=" + wins + ", losses=" + losses + ", veteran=" + veteran + ", inactive=" + inactive + ", freshBlood=" + freshBlood + ", hotStreak=" + hotStreak + '}';
        }
    }
}

