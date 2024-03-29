package org.example.getusermatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getusermatches.domain.MatchInfo;
import org.example.getusermatches.repository.UserMatchRepository;
import org.springframework.http.ResponseEntity;
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

    private final AtomicInteger key = new AtomicInteger(0);

    public void saveMatch(String matchId, String tier, String rank) {
        List<String> API_KEY = apiKeyService.getApiKeys();

        int curKey = key.updateAndGet(current -> (current >= 5_000) ? current % API_KEY.size() : (current + 1) % API_KEY.size());
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=";
        int attempts = 0;

        int MAX_RETRY = 3;
        while (attempts < MAX_RETRY) {
            try {
                ResponseEntity<MatchInfo> response = restTemplate.getForEntity(url + API_KEY.get(curKey), MatchInfo.class);
                MatchInfo matchInfo = response.getBody();
                if (matchInfo != null && matchInfo.getInfo().getQueueId() == 420) {
                    matchInfo.setMatchId(matchId);
                    matchInfo.setTier(tier);
                    matchInfo.setRank(rank);
                    userMatchRepository.upsertMatchInfo(matchInfo);
//                    log.info("매치 경기 저장 완료! key:{} | tier:{} | rank:{}", curKey, tier, rank);
                    return;
                }
            } catch (HttpClientErrorException e) {
                log.error("매치 경기 API 요청 실패: statuscode: {}", e.getStatusCode());
                attempts++;
                curKey = key.getAndIncrement() % API_KEY.size();
                if (attempts >= MAX_RETRY) {
                    log.error("매치 저장 실패: 최대 재시도 횟수 도달");
                }
            }
        }
    }
}
