package gg.garen.back.matchPrediction.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DetectDuplicateMatchService {
    private final Map<String, Boolean> matchId = new ConcurrentHashMap<>();

    public boolean isDuplicate(String id) {
        return matchId.containsKey(id);
    }

    public void markMatchId(String id) {
        if (matchId.size() >= 10_000) {
            matchId.clear();
        }
        matchId.put(id, true);
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void clearHashMapAtMidnight() {
        matchId.clear();
    }
}
