package org.example.getusermatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getusermatches.domain.MatchInfo;
import org.example.getusermatches.domain.PlayerInfo;
import org.example.getusermatches.repository.UserMatchRepository;
import org.example.getusermatches.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public void getUser(int offset) throws InterruptedException {
        Pageable pageable = PageRequest.of(offset, 1);
        Page<PlayerInfo> all = userRepository.findAll(pageable);
        List<PlayerInfo> content = all.getContent();

        for (int i = 0; i < content.size(); i++) {
            PlayerInfo playerInfo = content.get(i);
            String puuid = playerInfo.getPuuid();
            int apiKeyId = playerInfo.getApiKeyId();
            String apiKey = API_KEY.get(apiKeyId);
            HttpStatusCode matches = getMatches(puuid, apiKey);
            if (matches.is4xxClientError()) {
                log.info("getUser= {}", matches);
                Thread.sleep(120000); // 120초 대기
                i--;
            } else {
                Thread.sleep(1200); // 1.2초 대기
            }
            log.info("유저저장완료-{}", i);
        }
    }

    public HttpStatusCode getMatches(String puuid, String apiKey) throws InterruptedException {
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=100&api_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        HttpStatusCode statusCode = response.getStatusCode();

        if (statusCode.is4xxClientError()) {
            return statusCode;
        }

        List<String> result = response.getBody();

        for (int i = 0; i < result.size(); i++) {
            String matchId = result.get(i);
            HttpStatusCode httpStatusCode = saveMatch(matchId, apiKey);
            if (httpStatusCode.is4xxClientError()) {
                log.info("getMatches= {}", httpStatusCode);
                Thread.sleep(120000); // 120초 대기
                i--;
            } else {
                Thread.sleep(1200); // 1.2초 대기
            }
        }
        return statusCode;
    }

    public HttpStatusCode saveMatch(String matchId, String apiKey) {
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<MatchInfo> result = restTemplate.getForEntity(url, MatchInfo.class);
        MatchInfo matchInfo = result.getBody();

        if (matchInfo != null) {
            userMatchRepository.save(matchInfo);
        }
        return result.getStatusCode();
    }

}
