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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AsyncUserService {
    public List<ApiKey> apiKeys;
    public Map<Long, String> apiKeysMap;

    private final ApiKeyRepository apiKeyRepository;
    private final UserRiotApiRepository userRiotApiRepository;
    @Async
    public void setAllSummonerId(String tier, String rank, int startKeyIdx) throws InterruptedException{
        log.info("{} {} setAllSummonerId start key start : {}", tier, rank, startKeyIdx);
        LinkedList<PlayerInfo> playerInfosCache = new LinkedList<>();
        int page = 1;
        int keyIdx = startKeyIdx;
        while (true) {
            ApiKey apiKey = getKey(keyIdx);
            int responseCode = 0;
            //요청보내기
//            System.out.println("[getSummonerID] "+tier+rank+" "+ page + "start. keyIdx :" + keyIdx+"시작. ");
            try {
                URL url = new URL("https://kr.api.riotgames.com/lol/league-exp/v4/entries/RANKED_SOLO_5x5/" + tier + "/" + rank + "?page=" + page + "&api_key=" + apiKey.getApiKey());
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
                List<PlayerInfoDto> infos =  parseJsonResponseToPlayerInfoList(response.toString());

                if (infos.size() == 0){
                    break;
                }

                //잘 가져왔을경우 캐시에 저장.
                LinkedList<PlayerInfo> playerInfos = playerInfoDtoListToPlayerInfoList(infos, apiKey.getId());
                playerInfosCache.addAll(playerInfos);

            } catch (IOException e) {
                //키막혔을경우
                if (responseCode == 429) {
                    System.out.println("[getSummonerID] apiKey request limit. nextKey during " + tier + rank + " page : " + page + ", key : " + keyIdx);
                    Thread.sleep(100);
                    page--;
                }
                else{
                    System.out.println("[getSummonerId] UnCatchable error! reponseCode : " + responseCode);
                    System.out.println("[getSummonerId] error during " + tier + rank + " page : " + page + ", key : " + keyIdx);
                }
            }

            //다음키가없는경우 0 으로 돌아간다.
            if (++keyIdx >= getKeySize()) {
                keyIdx = 0;
            }

            page++;
        }


//        //한티어, 랭크에대한 작업이 끝나면, db에 저장한다.
//        long startTime = System.currentTimeMillis();
//        System.out.println("[getSummonerId]  "+tier+" "+rank+" 데이터쌓기 시작.");
//        userRiotApiRepository.saveAll(playerInfosCache);
//        long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        System.out.println("[getSummonerId]  "+tier+" "+rank+"데이터 저장 "+ elapsedTime/1000 +"초 걸림");
        System.out.println("[getSummonerId]  "+tier+" "+rank+" done. call setAllPuuid");


        setAllPuuid(tier, rank, playerInfosCache);
    }

//    @Async
    public void setAllPuuid(String tier, String rank, LinkedList<PlayerInfo> playerInfos) throws InterruptedException{

        System.out.println(tier + rank + " setAllPuuid start");

//        LinkedList<PlayerInfo> playerInfos = userRiotApiRepository.findByTierAndRank(tier, rank);
        LinkedList<PlayerInfo> resultList = new LinkedList<>();

        //db에서 유저를 티어/랭크 별로가져옴.
        while(!playerInfos.isEmpty()) {
            int responseCode = 0;
            PlayerInfo pi = playerInfos.poll();
            try {
                //가져온 유저에 대해, puuid 달라는 요청을보낸다.
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

                //puuid가 기록된 친구들을 PlayerInfo 리스트에 저장한다.
                resultList.add(pi);
            } catch (IOException e) {
                //not found가 뜰경우, 해당 유저를 삭제한다.
                if(responseCode == 404){
                    System.out.println("[getPuuid] summonerId로 조회했을떄 해당하는 Puuid가 없어요.");
                }
                //429가 뜰경우, 해당유저를 다시 큐에넣는다.
                else if(responseCode == 429) {
//                    System.out.println("[getPuuid] apiKey request limit"+ tier + rank+", key : "+pi.getApiKeyId());
                    Thread.sleep(100);
                    playerInfos.add(pi);
                }
                //그외의 경우, 확인해본다.
                else{
                    e.printStackTrace();
                    System.out.println("[getPuuid] error during " + tier + rank+". response code : "+responseCode);
                }

            }
        }
        System.out.println("[getPuuid] "+tier+" "+rank+" done. call setAllNameAndTag");

        playerInfos = resultList;

        setAllNameAndTag(tier, rank, playerInfos);
    }

//    @Async
    public void setAllNameAndTag(String tier, String rank, LinkedList<PlayerInfo> playerInfos) throws InterruptedException{
        System.out.println(tier + rank + " setAllNameAndTag start");
//        LinkedList<PlayerInfo> playerInfos = userRiotApiRepository.findByTierAndRank(tier, rank);

        //db에 저장할 유저 셋 메모리에 모아놓는다.
        LinkedList<PlayerInfo> resultSet = new LinkedList<>();

        //유저를 가져온다.
        while(!playerInfos.isEmpty()) {
            int responseCode = 0;
            PlayerInfo pi = playerInfos.poll();
            try {
                //가져온 유저에 대해, Name, Tag 달라는 요청을보낸다.

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

                //Name,tag가 기록된 친구들을 PlayerInfo 리스트에 저장한다.
                resultSet.add(pi);
            } catch (IOException e) {
                //not found가 뜰경우, 해당 유저는 저장하지 않는다.
                if(responseCode == 404){
                    System.out.println("[getNameAndTag] " + tier + rank+"해당 puuid "+pi.getPuuid()+"에 해당하는 name이랑 tag가 없어요.");
                }
                //429가 뜰경우, 해당유저를 다시 큐에넣는다.
                else if(responseCode == 429) {
//                    System.out.println("[getNameAndTag] apiKey request limit" + tier + rank+", key : "+pi.getApiKeyId());
                    Thread.sleep(100);
                    playerInfos.add(pi);
                }
                //그 외의 경우, 확인해본다.
                else{
                    e.printStackTrace();
                    System.out.println("[getNameAndTag] error during " + tier + rank+". response code : "+responseCode);
                }

            }

        }

        //한티어, 랭크에대한 작업이 끝나면, db에 저장한다.
        for (PlayerInfo entity : resultSet) {
            if(entity.getTagLine() == null || entity.getSummonerName() == null) continue;

            PlayerInfo existingEntity = (PlayerInfo) userRiotApiRepository.findBySummonerNameAndTagLine(entity.getSummonerName(), entity.getTagLine()).orElse(null);

            if (existingEntity != null) {
                // 이미 존재하는 경우, ID를 설정하여 업데이트를 활성화
                System.out.println("이미 존재하는 player : " + existingEntity);
                System.out.println("갱신정보 : "+ entity);
                entity.setPlayerId(existingEntity.getPlayerId());
            }
        }

        long startTime = System.currentTimeMillis();
        userRiotApiRepository.saveAll(resultSet);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("데이터 저장 "+ elapsedTime/1000 +"초 걸림");
        System.out.println("[getNameAndTag]  "+tier+" "+rank+" done");
    }


    LinkedList<PlayerInfo> playerInfoDtoListToPlayerInfoList(List<PlayerInfoDto> list, long apiKeyId) {
        List<PlayerInfo> tempList = list.stream()
                .map(dto -> playerInfoDtoToPlayerInfo(dto, apiKeyId))
                .toList();

        return new LinkedList<>(tempList);
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
        entity.setSummonerName(null); //중간처리과정에서 중복오류를 막는다.
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
