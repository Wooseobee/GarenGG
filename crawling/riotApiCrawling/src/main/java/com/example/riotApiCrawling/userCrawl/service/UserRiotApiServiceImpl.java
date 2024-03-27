package com.example.riotApiCrawling.userCrawl.service;

import com.example.riotApiCrawling.apiKey.entity.ApiKey;
import com.example.riotApiCrawling.apiKey.repository.ApiKeyRepository;
import com.example.riotApiCrawling.userCrawl.dto.PlayerInfoDto;
import com.example.riotApiCrawling.userCrawl.entity.PlayerInfo;
import com.example.riotApiCrawling.userCrawl.repository.UserRiotApiRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserRiotApiServiceImpl {
    public String[] tiers = {"CHALLENGER", "GRANDMASTER", "MASTER", "DIAMOND", "PLATINUM", "GOLD", "SILVER", "BRONZE", "IRON"};
    public String[] ranks = {"", "I", "II", "III", "IV"};
    public List<ApiKey> apiKeys;
    public Map<Long, String> apiKeysMap;
    public int responseCode;

    private final ApiKeyRepository apiKeyRepository;
    private final UserRiotApiRepository userRiotApiRepository;

    public void setUsers(){
        long startTime = System.currentTimeMillis();
        setAllSummonerId();
        setAllPuuid();
        setAllNameAndTag();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("유저 쌓기 끝: " + elapsedTime + " 밀리초");
    }

    public void setAllSummonerId() {
        System.out.println("setAllSummonerId start");
        for (int tierIdx = 0; tierIdx < tiers.length; tierIdx++) {
            int rankLength = ranks.length - 1;
            if (tierIdx <= 2) rankLength = 1;

            for (int rankIdx = 1; rankIdx <= rankLength; rankIdx++) {
                int page = 1;
                int keyIdx = 0;
                while (true) {
                    ApiKey apiKey = getKey(keyIdx);
//                    요청보내기
                    System.out.println("[getSummonerID] "+tiers[tierIdx]+ranks[rankIdx]+" "+ page + "start. keyIdx :" + keyIdx);
                    try {
                        responseCode = 0;
                        List<PlayerInfoDto> infos = fetchLeagueEntries(tiers[tierIdx], ranks[rankIdx], page, apiKey.getApiKey());

                        //infos 출력
//                        for(int i = 0; i < infos.size(); i++){
//                            System.out.println(i + ": " + infos.get(i).getSummonerName());
//                        }

                        //요청이 빈 배열일 경우 break; 이거 확인.
//                        System.out.println("인포 크기 : " + infos.size());
                        if (infos.size() == 0) break;

                        //잘 가져왔을경우 DB에 저장
                        List<PlayerInfo> PlayerInfos = playerInfoDtoListToPlayerInfoList(infos, apiKey.getId());
                        userRiotApiRepository.saveAll(PlayerInfos);

                    } catch (IOException e) {
                        //키막혔을경우
                        if (responseCode == 429) {
                            System.out.println("[getSummonerID] apiKey request limit. nextKey");
                            page--;
                        } else {
                            System.out.println("[getSummonerId] UnCatchable error! reponseCode : " + responseCode);
                            System.out.println("[getSummonerId] error during " + tiers[tierIdx] + ranks[rankIdx] + " page : " + page + ", key : " + keyIdx);
                        }
                    }
                    //다음키가없는경우 0 으로 돌아간다.
                    if (++keyIdx >= getKeySize()) {
                        keyIdx = 0;
                    }
                    page++;
                }
                System.out.println("[getSummonerId] " + tiers[tierIdx] + ranks[rankIdx] +", key : " + keyIdx + "summonerID input done");
            }
            System.out.println("[getSummonerId] " + tiers[tierIdx]  + "summonerID input All done");
        }

        System.out.println("setAllSummonerId done");
    }

    public void setAllPuuid(){
        System.out.println("setAllPuuid start");
        //db에서 티어/랭크 별로가져옴.
        for (int tierIdx = 0; tierIdx < tiers.length; tierIdx++) {
            int rankLength = ranks.length - 1;
            if (tierIdx <= 2) rankLength = 1;

            for(int rankIdx = 1; rankIdx <= rankLength; rankIdx++) {
                responseCode = 0;

                //db에 저장할 유저 셋 메모리에 모아놓는다.
                Set<PlayerInfo> resultSet = new HashSet<>();

                //유저를 가져온다.
                LinkedList<PlayerInfo> list = userRiotApiRepository.findByTierAndRank(tiers[tierIdx], ranks[rankIdx]);
                while(!list.isEmpty()) {
                    PlayerInfo pi = list.poll();
                    try {
                        //가져온 유저에 대해, puuid 달라는 요청을보낸다.
                        fetchPuuid(pi);
                        //puuid가 기록된 친구들을 PlayerInfo 리스트에 저장한다.
                        resultSet.add(pi);
                        throw new IOException();
                    } catch (IOException e) {
                        //not found가 뜰경우, 해당 유저를 삭제한다.
                        if(responseCode == 404){
                            System.out.println("[getPuuid] puuid없는 쓰레기같은 trash 값. 삭제하면 널체크안해도되고, 안하면 널체크해야함.");
                        }
                        //429가 뜰경우, 해당유저를 다시 큐에넣는다.
                        else if(responseCode == 429) {
                            System.out.println("[getPuuid] apiKey request limit");
                            list.add(pi);
                        }
                        //그외의 경우, 확인해본다.
                        else{
                            e.printStackTrace();
                            System.out.println("[getPuuid] error during " + tiers[tierIdx] + ranks[rankIdx]+". response code : "+responseCode);
                        }

                    }

                }
                //한티어, 랭크에대한 작업이 끝나면, db에 저장한다.
                userRiotApiRepository.saveAll(resultSet);
                System.out.println("[getPuuid] "+tiers[tierIdx]+" "+ranks[rankIdx]+" done");
            }
            System.out.println("[getPuuid] "+tiers[tierIdx] +"All done");
        }

        System.out.println("setAllPuuid done");
    }

    private void setAllNameAndTag() {
        System.out.println("setAlLNameAndTag start");
        //db에서 티어/랭크 별로가져옴.
        for (int tierIdx = 0; tierIdx < tiers.length; tierIdx++) {
            int rankLength = ranks.length - 1;
            if (tierIdx <= 2) rankLength = 1;

            for(int rankIdx = 1; rankIdx <= rankLength; rankIdx++) {
                responseCode = 0;

                //db에 저장할 유저 셋 메모리에 모아놓는다.
                Set<PlayerInfo> resultSet = new HashSet<>();

                //유저를 가져온다.
                LinkedList<PlayerInfo> list = userRiotApiRepository.findByTierAndRank(tiers[tierIdx], ranks[rankIdx]);
                while(!list.isEmpty()) {
                    PlayerInfo pi = list.poll();
                    try {
                        //가져온 유저에 대해, Name, Tag 달라는 요청을보낸다.
                        fetchNameAndTag(pi);
                        //Name,tag가 기록된 친구들을 PlayerInfo 리스트에 저장한다.
                        resultSet.add(pi);
                        throw new IOException();
                    } catch (IOException e) {
                        //not found가 뜰경우, 해당 유저를 삭제한다.
                        if(responseCode == 404){
                            System.out.println("[getNameAndTag] getNameAndTag없는 쓰레기같은 trash 값. 삭제하면 널체크안해도되고, 안하면 널체크해야함.");
                        }
                        //429가 뜰경우, 해당유저를 다시 큐에넣는다.
                        else if(responseCode == 429) {
                            System.out.println("[getNameAndTag] apiKey request limit");
                            list.add(pi);
                        }
                        //그외의 경우, 확인해본다.
                        else{
                            e.printStackTrace();
                            System.out.println("[getNameAndTag] error during " + tiers[tierIdx] + ranks[rankIdx]+". response code : "+responseCode);
                        }

                    }

                }
                //한티어, 랭크에대한 작업이 끝나면, db에 저장한다.
                userRiotApiRepository.saveAll(resultSet);
                System.out.println("[getNameAndTag]  "+tiers[tierIdx]+" "+ranks[rankIdx]+" done");
            }
            System.out.println("[getNameAndTag] "+tiers[tierIdx] +"All done");
        }

        System.out.println("setAllNameAndTag done");
    }


    public List<PlayerInfoDto> fetchLeagueEntries(String tier, String rank, int page, String key) throws IOException {
        URL url = new URL("https://kr.api.riotgames.com/lol/league-exp/v4/entries/RANKED_SOLO_5x5/" + tier + "/" + rank + "?page=" + page + "&api_key=" + key);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 요청 메서드 설정 (GET 방식)
        connection.setRequestMethod("GET");

        // 응답 코드 확인. 입/출력스트림가져오면서 암시적으로 네트워크연결.
        responseCode = connection.getResponseCode();

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

        //플레이어 정보 담기
        return parseJsonResponseToPlayerInfoList(response.toString());
    }

    public void fetchPuuid(PlayerInfo pi) throws IOException{
        URL url = new URL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/"+pi.getSummonerId()+ "?api_key=" +getKeyString(pi.getApiKeyId()));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 요청 메서드 설정 (GET 방식)
        connection.setRequestMethod("GET");

        // 응답 코드 확인. 입/출력스트림가져오면서 암시적으로 네트워크연결.
        responseCode = connection.getResponseCode();

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

        //플레이어 정보 담기
        Map<String, String> map =  parseJsonResponseToMap(response.toString());

        //PlayerInfo 갱신
        pi.setPuuid(map.get("puuid"));
    }

    private void fetchNameAndTag(PlayerInfo pi) throws IOException{
        URL url = new URL("https://asia.api.riotgames.com/riot/account/v1/accounts/by-puuid/"+pi.getPuuid()+"?api_key="+getKeyString(pi.getApiKeyId()));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 요청 메서드 설정 (GET 방식)
        connection.setRequestMethod("GET");

        // 응답 코드 확인. 입/출력스트림가져오면서 암시적으로 네트워크연결.
        responseCode = connection.getResponseCode();

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

        //플레이어 정보 담기
        Map<String, String> map =  parseJsonResponseToMap(response.toString());

        //PlayerInfo 갱신
        pi.setSummonerName(map.get("gameName"));
        pi.setTagLine(map.get("tagLine"));
    }

    List<PlayerInfo> playerInfoDtoListToPlayerInfoList(List<PlayerInfoDto> list, long apiKeyId) {
        return list.stream()
                .map(dto -> playerInfoDtoToPlayerInfo(dto, apiKeyId))
                .collect(Collectors.toList());
    }

    PlayerInfo playerInfoDtoToPlayerInfo(PlayerInfoDto dto, long apiKeyId) {
        PlayerInfo entity = new PlayerInfo();
        entity.setPuuid(dto.getPuuid());
        entity.setTagLine(dto.getTagLine());
        entity.setLeagueId(dto.getLeagueId());
        entity.setQueueType(dto.getQueueType());
        entity.setTier(dto.getTier());
        entity.setRank(dto.getRank());
        entity.setSummonerId(dto.getSummonerId());
        entity.setSummonerName(dto.getSummonerName());
        entity.setLeaguePoints(dto.getLeaguePoints());
        entity.setWins(dto.getWins());
        entity.setLosses(dto.getLosses());
        entity.setVeteran(dto.isVeteran());
        entity.setInactive(dto.isInactive());
        entity.setFreshBlood(dto.isFreshBlood());
        entity.setHotStreak(dto.isHotStreak());
        entity.setApiKeyId(apiKeyId);
        return entity;
    }

    public ApiKey getKey(int keyIdx) {
        if (apiKeys == null) {
            apiKeys = apiKeyRepository.findAll();
        }
        return apiKeys.get(keyIdx);
    }

    public String getKeyString(Long keyId) {
        if (apiKeysMap == null) {
            getKey(0);
            apiKeysMap = new HashMap<>();
            for(ApiKey apiKey : apiKeys){
                apiKeysMap.put(apiKey.getId(), apiKey.getApiKey());
            }
        }
        return apiKeysMap.get(keyId);
    }

    public int getKeySize() {
        return apiKeys.size();
    }

    private List<PlayerInfoDto> parseJsonResponseToPlayerInfoList(String jsonResponse) {
        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, new TypeToken<List<PlayerInfoDto>>() {
        }.getType());
    }

    private Map<String, String> parseJsonResponseToMap(String jsonResponse) {
        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, new TypeToken<Map<String, String>>() {
        }.getType());
    }
}
