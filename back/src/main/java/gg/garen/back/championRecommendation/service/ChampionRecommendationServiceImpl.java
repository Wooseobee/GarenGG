package gg.garen.back.championRecommendation.service;

import gg.garen.back.championRecommendation.dto.response.ResponseGetChampionRecommendationDto;
import gg.garen.back.common.domain.mongo.MatchInfo;
import gg.garen.back.common.domain.mongo.PlayerMost;
import gg.garen.back.common.domain.mysql.PlayerInfo;
import gg.garen.back.common.repository.PlayerInfoRepository;
import gg.garen.back.common.repository.PlayerMostRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChampionRecommendationServiceImpl implements ChampionRecommendationService {

    private final PlayerInfoRepository playerInfoRepository;
    private final PlayerMostRepository playerMostRepository;

//    @Value("${riot.apiKeys}")
//    private List<String> API_KEY;

    @Override
    public ResponseGetChampionRecommendationDto getChampionRecommendation(String summonerName, String tagLine) {

        SecureRandom secureRandom = new SecureRandom();
        int API_KEY_index = -1;

        PlayerInfo findedPlayerInfo = playerInfoRepository.findBySummonerNameAndTagLine(summonerName, tagLine);
        // MySQL에 없다
        if (findedPlayerInfo == null) {

            // 롤 API로 유저 정보 얻고 MySQL에 저장
//            API_KEY_index = secureRandom.nextInt(API_KEY.size());
//            AccountDto accountDto = getAccountByRiotId(summonerName, tagLine, API_KEY.get(API_KEY_index));

            PlayerInfo newPlayerInfo = new PlayerInfo();
//            newPlayerInfo.setPuuid(accountDto.getPuuid());
//            newPlayerInfo.setSummonerName(accountDto.getGameName());
//            newPlayerInfo.setTagLine(accountDto.getTagLine());
            newPlayerInfo.setApiKeyId(API_KEY_index);
            findedPlayerInfo = playerInfoRepository.save(newPlayerInfo);

        }

        PlayerMost findedPlayerMost = playerMostRepository.findById(findedPlayerInfo.getPlayerId()).orElse(null);
        // MongoDB에 없다
        if (findedPlayerMost == null) {

            // 롤 API로 Most 정보 얻고 몽고 디비에 저장
//            List<String> matches = getMatchesByPuuid(findedPlayerInfo.getPuuid(), API_KEY.get(findedPlayerInfo.getApiKeyId()));
//            System.out.println(getMatchInfoByMatch(matches.get(0), API_KEY.get(findedPlayerInfo.getApiKeyId())).getInfo().getGameDuration());



        }

        // FAST API 호출

        return null;
    }

    public AccountDto getAccountByRiotId(String gameName, String tagLine, String apiKey) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}?api_key={apiKey}";
        return restTemplate.getForObject(url, AccountDto.class, gameName, tagLine, apiKey);
    }

    public List<String> getMatchesByPuuid(String puuid, String apiKey) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?queue=420&count=100&api_key=" + apiKey;
        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {
                }
        );
        return response.getBody();
    }

    public MatchInfo getMatchInfoByMatch(String match, String apiKey) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/{match}?api_key={apiKey}";
        return restTemplate.getForObject(url, MatchInfo.class, match, apiKey);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class AccountDto {
        private String puuid;
        private String gameName;
        private String tagLine;
    }
}


