package org.example.getusermatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getusermatches.domain.PlayerInfo;
import org.example.getusermatches.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final SaveMatchService saveMatchService;
    private final APIKeyService apiKeyService;

    public void saveUserMatch(String startTime, String endTime) throws InterruptedException {
        List<String> API_KEY = apiKeyService.getApiKeys();
        int idx = 0;
        Pageable pageable = PageRequest.of(idx, 10);
        Page<PlayerInfo> find = userRepository.findAll(pageable);

        while (!find.isEmpty()) {
            List<PlayerInfo> content = find.getContent();

            for (int i = 0; i < content.size(); i++) {
                PlayerInfo playerInfo = content.get(i);
                String puuid = playerInfo.getPuuid();
                if (puuid == null) continue;
                int apiKeyId = playerInfo.getApiKeyId();
                String apiKey = API_KEY.get(apiKeyId - 1);
                HttpStatusCode matches = getMatches(apiKeyId - 1, puuid, apiKey, startTime, endTime, playerInfo.getTier(), playerInfo.getRank());
                if (matches.is4xxClientError() || matches.is5xxServerError()) {
                    i--;
                    Thread.sleep(12000);
                }
            }
            idx++;
            pageable = PageRequest.of(idx, 10);
            find = userRepository.findAll(pageable);
        }
    }

    public HttpStatusCode getMatches(int apiKeyId, String puuid, String apiKey, String startTime, String endTime, String tier, String rank) throws InterruptedException {
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?startTime=" + startTime + "&endTime=" + endTime + "&queue=420&start=0&count=100&api_key=" + apiKey;
        final int MAX_RETRY = 3;
        int retryCount = 0;
        ResponseEntity<List> response;
        HttpStatusCode statusCode;

        do {
            response = restTemplate.getForEntity(url, List.class);
            statusCode = response.getStatusCode();

            if (statusCode.is2xxSuccessful()) {
                break;
            } else if (statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
                retryCount++;
                Thread.sleep(12000);
            }
        } while ((statusCode.is4xxClientError() || statusCode.is5xxServerError()) && retryCount < MAX_RETRY);

        if (retryCount == MAX_RETRY) {
            log.error("최대 재시도 횟수에 도달 - 에러 코드: {}", statusCode);
            return statusCode;
        }

        List<String> result = response.getBody();

        if (result != null) {
            for (String matchId : result) {
                saveMatchService.saveMatch(matchId, tier, rank, apiKeyId);
            }
        }
        return statusCode;
    }
}
