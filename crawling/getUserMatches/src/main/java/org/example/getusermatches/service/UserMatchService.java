package org.example.getusermatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getusermatches.domain.PlayerInfo;
import org.example.getusermatches.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMatchService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final SaveMatchService saveMatchService;

    @Value("${riot.apiKeys}")
    private final List<String> API_KEYS;

    public void saveUserMatch(int apiKey, String startTime, String endTime) throws InterruptedException {
        int idx = 0;
        Pageable pageable = PageRequest.of(idx, 10);
        Page<PlayerInfo> find = userRepository.findAllByApiKeyId(pageable, apiKey);

        while (!find.isEmpty()) {
            List<PlayerInfo> content = find.getContent();

            for (int i = 0; i < content.size(); i++) {
                PlayerInfo playerInfo = content.get(i);
                String puuid = playerInfo.getPuuid();
                if (puuid == null) continue;
                HttpStatusCode matches = getMatches(apiKey, puuid, startTime, endTime, playerInfo.getTier(), playerInfo.getRank());
                if (matches.is4xxClientError() || matches.is5xxServerError()) {
                    i--;
                }
                Thread.sleep(12000);
            }
            pageable = PageRequest.of(++idx, 10);
            find = userRepository.findAllByApiKeyId(pageable, apiKey);
            log.info("매치 저장 완료 - apiKey : {} / idx : {}", apiKey, idx);
        }
        log.info("매치 {}명 저장 완료 - apiKey : {}", idx * 10, apiKey);
    }

    public HttpStatusCode getMatches(int apiKey, String puuid, String startTime, String endTime, String tier, String rank) throws InterruptedException {
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?startTime=" + startTime + "&endTime=" + endTime + "&queue=420&start=0&count=100&api_key=" + API_KEYS.get(apiKey);
        final int MAX_RETRY = 3;
        int retryCount = 0;
        ResponseEntity<List> response = null;
        HttpStatusCode statusCode;

        do {
            try {
                response = restTemplate.getForEntity(url, List.class);
                statusCode = response.getStatusCode();
                break;
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode().value() == 400) {
                    return HttpStatus.BAD_REQUEST;
                } else {
                    retryCount++;
                    Thread.sleep(12000);
                    statusCode = e.getStatusCode();
                }
            }
        } while (retryCount < MAX_RETRY);

        if (retryCount >= MAX_RETRY) {
            log.error("최대 재시도 횟수에 도달 - 에러 코드: {}", statusCode);
            return statusCode;
        }

        List<String> result = response.getBody();
        if (result != null) {
            for (String matchId : result) {
                saveMatchService.saveMatch(matchId, tier, rank);
            }
        }
        return statusCode;
    }
}
