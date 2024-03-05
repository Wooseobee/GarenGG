package org.example.getusermatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getusermatches.domain.MatchInfo;
import org.example.getusermatches.domain.PlayerInfo;
import org.example.getusermatches.repository.UserMatchRepository;
import org.example.getusermatches.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMatchService {

    private final UserMatchRepository userMatchRepository;
    private final UserRepository userRepository;

    @Value("${riot.apiKeys}")
    private List<String> API_KEY;

    public void getUser(String tier, String rankNum) throws InterruptedException {
        List<PlayerInfo> content = userRepository.findAllByTierAndRank(tier, rankNum);

        for (int i = 0; i < content.size(); i++) {
            PlayerInfo playerInfo = content.get(i);
            String puuid = playerInfo.getPuuid();
            if (puuid == null) continue;
            int apiKeyId = playerInfo.getApiKeyId();
            String apiKey = API_KEY.get(apiKeyId);
            HttpStatusCode matches = getMatches(puuid, apiKey);
            if (matches.is4xxClientError()) {
                log.info("getUser= {}", matches);
                i--;
            } else {
                Thread.sleep(1200); // 1.2초 대기
            }
            log.info("유저저장완료 tier:{}", tier);
        }
    }

    public HttpStatusCode getMatches(String puuid, String apiKey) throws InterruptedException {
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=100&api_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            HttpStatusCode statusCode = response.getStatusCode();

            List<String> result = response.getBody();

            for (int i = 0; i < result.size(); i++) {
                String matchId = result.get(i);
                HttpStatusCode httpStatusCode = saveMatch(matchId, apiKey);
                if (httpStatusCode.is4xxClientError()) {
                    log.info("getMatches= {}", httpStatusCode);
                    i--;
                } else {
                    Thread.sleep(1200); // 1.2초 대기
                }
            }
            return statusCode;
        } catch (Exception e) {
            Thread.sleep(12000); // 12초 대기
            return HttpStatusCode.valueOf(429);
        }
    }

    public HttpStatusCode saveMatch(String matchId, String apiKey) throws InterruptedException {
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MatchInfo> result;
        try {
            result = restTemplate.getForEntity(url, MatchInfo.class);
            MatchInfo matchInfo = result.getBody();
            if (matchInfo != null && matchInfo.getInfo().getQueueId() == 420) {
                matchInfo.setMatchId(matchId);
                userMatchRepository.upsertMatchInfo(matchInfo);
            }
            return result.getStatusCode();
        } catch (Exception e) {
            Thread.sleep(12000); // 12초 대기
            return HttpStatusCode.valueOf(429);
        }
    }

}
