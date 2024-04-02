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
        matchId.put(id, true);
        for (String s : matchId.keySet()) {
            System.out.println(s);
        }
        System.out.println("----");
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void clearHashMapAtMidnight() {
        matchId.clear();
    }
}
