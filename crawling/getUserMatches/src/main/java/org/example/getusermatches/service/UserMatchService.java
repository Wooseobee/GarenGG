package org.example.getusermatches.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getusermatches.domain.ApiKey;
import org.example.getusermatches.domain.MatchInfo;
import org.example.getusermatches.domain.PlayerInfo;
import org.example.getusermatches.repository.ApiKeyRepository;
import org.example.getusermatches.repository.UserMatchRepository;
import org.example.getusermatches.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMatchService {

    private final UserMatchRepository userMatchRepository;
    private final UserRepository userRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final RestTemplate restTemplate;

    private List<String> API_KEY;
    private final AtomicInteger key = new AtomicInteger(0);

    @PostConstruct
    public void initApiKeys() {
        API_KEY = apiKeyRepository.findAll().stream()
                .map(ApiKey::getApiKey)
                .collect(Collectors.toList());
    }

    public void saveUserMatch(String startTime, String endTime) throws InterruptedException {
        int idx = 0;
        Pageable pageable = PageRequest.of(idx, 10);
        Page<PlayerInfo> find = userRepository.findAll(pageable);
        List<PlayerInfo> content = find.getContent();

        while (find.hasNext()) {
            for (int i = 0; i < content.size(); i++) {
                PlayerInfo playerInfo = content.get(i);
                String puuid = playerInfo.getPuuid();
                if (puuid == null) continue;
                String apiKey = API_KEY.get(playerInfo.getApiKeyId() - 1);
                HttpStatusCode matches = getMatches(puuid, apiKey, startTime, endTime, playerInfo.getTier(), playerInfo.getRank());
                if (matches.is4xxClientError()) {
                    log.info("getUser= {}", matches);
                    i--;
                }
                log.info("유저저장완료 - idx:{}", idx + 10);
            }
            idx += 10;
            pageable = PageRequest.of(idx, 10);
            find = userRepository.findAll(pageable);
            content = find.getContent();
        }
    }

    public HttpStatusCode getMatches(String puuid, String apiKey, String startTime, String endTime, String tier, String rank) throws InterruptedException {
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?startTime=" + startTime + "&endTime=" + endTime + "&queue=420&start=0&count=100&api_key=" + apiKey;
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            HttpStatusCode statusCode = response.getStatusCode();

            List<String> result = response.getBody();

            for (int i = 0; i < result.size(); i++) {
                String matchId = result.get(i);
                saveMatch(matchId, tier, rank);
            }
            return statusCode;
        } catch (Exception e) {
            Thread.sleep(12000); // 12초 대기
            return HttpStatus.TOO_MANY_REQUESTS;
        }
    }

    @Async("taskExecutor")
    public void saveMatch(String matchId, String tier, String rank) {
        int curKey = key.getAndIncrement() % 5_000;
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + API_KEY.get(curKey);
        ResponseEntity<MatchInfo> result;
        result = restTemplate.getForEntity(url, MatchInfo.class);
        MatchInfo matchInfo = result.getBody();

        if (matchInfo != null && matchInfo.getInfo().getQueueId() == 420) {
            matchInfo.setMatchId(matchId);
            matchInfo.setTier(tier);
            matchInfo.setRank(rank);
            userMatchRepository.upsertMatchInfo(matchInfo);
        }
    }

}
