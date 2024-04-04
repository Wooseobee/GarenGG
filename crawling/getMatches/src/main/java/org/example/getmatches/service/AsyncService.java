package org.example.getmatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getmatches.domain.Choice;
import org.example.getmatches.domain.mysql.DuoRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncService {

    private final CombinationService combinationService;
    @Async("taskExecutor")
    public void processCombinations(List<Choice> champions, String matchId, boolean isVictory) {
        for (int i = 0; i < champions.size(); i++) {
            for (int j = i + 1; j < champions.size(); j++) {
                DuoRecord duoRecord = createCombination(champions.get(i), champions.get(j));
                if (isValidCombination(duoRecord)) {
                    combinationService.saveCombination(duoRecord, matchId, isVictory);
                }
            }
        }
    }

    private DuoRecord createCombination(Choice champion1, Choice champion2) {
        DuoRecord duoRecord = new DuoRecord();
        duoRecord.setChampion1(Long.valueOf(champion1.getChampionId()));
        duoRecord.setLane1(champion1.getLane());
        duoRecord.setChampion2(Long.valueOf(champion2.getChampionId()));
        duoRecord.setLane2(champion2.getLane());
        return duoRecord;
    }


    private boolean isValidCombination(DuoRecord match) {
        return match.getChampion1() != null && match.getChampion2() != null && match.getLane1() != null && match.getLane2() != null;
    }
}
