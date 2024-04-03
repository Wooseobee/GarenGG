package org.example.getusermatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getusermatches.domain.MatchInfo;
import org.example.getusermatches.repository.UserMatchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaveMatchService {
    private final UserMatchRepository userMatchRepository;
    private final RestTemplate restTemplate;
    private final APIKeyService apiKeyService;
    private final int API_KEY_SIZE = 5_000;

    private final AtomicInteger key = new AtomicInteger(0);


    @Async("taskExecutor")
    public void getMatches(int apiKeyId, String puuid, String apiKey, String startTime, String endTime, String tier, String rank) {
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?startTime=" + startTime + "&endTime=" + endTime + "&queue=420&start=0&count=100&api_key=" + apiKey;
        ResponseEntity<List> response;
        do {
            try {
                response = restTemplate.getForEntity(url, List.class);
                List<String> result = response.getBody();

                if (result != null) {
                    for (String matchId : result) {
                        saveMatch(matchId, tier, rank);
                    }
                }
                return;
            } catch (Exception e) {
                log.info("재시도>> apiKey : {}", apiKeyId);
            }
        } while (true);
    }

    @Async("taskExecutor")
    public void saveMatch(String matchId, String tier, String rank) {
        List<String> API_KEY = apiKeyService.getApiKeys();
        int curKey = key.getAndIncrement() % 5_000;
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=";

        while (true) {
            try {
                if (key.get() >= 100_000_000) {
                    key.set(0);
                }
                ResponseEntity<MatchInfo> response = restTemplate.getForEntity(url + API_KEY.get(curKey), MatchInfo.class);
                MatchInfo matchInfo = response.getBody();
                if (matchInfo != null && matchInfo.getInfo().getGameDuration() >= 900) {
                    matchInfo.setMatchId(matchId);
                    matchInfo.setTier(tier);
                    matchInfo.setRank(rank);
                    userMatchRepository.save(matchInfo);
                } else {
                    log.info("이상한 경기 처리 ! curKey:{} | matchId:{}", curKey, matchId);
                }
                return;
            } catch (HttpClientErrorException e) {
                curKey = key.getAndIncrement() % API_KEY_SIZE;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
