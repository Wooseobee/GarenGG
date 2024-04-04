package gg.garen.back.championRecommendation.service;

import gg.garen.back.common.domain.mongo.MatchInfo;
import gg.garen.back.common.service.ApiKeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private final ApiKeyUtils apiKeyUtils;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Void> fillReceivedMatchInfos(List<MatchInfo> receivedMatchInfos, String receiveMatchId) {

        while (true) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = "https://asia.api.riotgames.com/lol/match/v5/matches/{match}?api_key={apiKey}";
                MatchInfo receivedMatchInfo = restTemplate.getForObject(url, MatchInfo.class, receiveMatchId, apiKeyUtils.getOneApiKey().getApiKey());
                receivedMatchInfos.add(receivedMatchInfo);
                break;
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {

                } else {
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }

        return CompletableFuture.completedFuture(null);
    }

}
