package org.example.getmatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getmatches.domain.Choice;
import org.example.getmatches.domain.mysql.DuoRecord;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncService implements ApplicationContextAware {

    private final CombinationService combinationService;
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
    @Async("taskExecutor")
    public void processCombinations(List<Choice> champions, String matchId, boolean isVictory) {
        for (int i = 0; i < champions.size(); i++) {
            for (int j = i + 1; j < champions.size(); j++) {
                DuoRecord duoRecord = createCombination(champions.get(i), champions.get(j));
                if (isValidCombination(duoRecord)) {
                    try {
                        combinationService.saveCombination(duoRecord, matchId, isVictory);
                    } catch (Exception e) {
                        retryProcessCombinations(duoRecord, matchId, isVictory, 1);
                    }
                }
            }
        }
    }

    private void retryProcessCombinations(DuoRecord duoRecord, String matchId, boolean isVictory, int attempt) {
        if (attempt > 3) {
            log.info("조합 저장 실패 - matchId: {}", matchId);
            return;
        }

        try {
            Thread.sleep(1000L * attempt); // 재시도 사이에 지연 시간 추가
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt(); // 현재 스레드의 인터럽트 상태 복원
            return;
        }

        try {
            AsyncService self = context.getBean(AsyncService.class);
            self.processCombinationsWithNewTransaction(duoRecord, matchId, isVictory);
        } catch (Exception e) {
            retryProcessCombinations(duoRecord, matchId, isVictory, attempt + 1);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processCombinationsWithNewTransaction(DuoRecord duoRecord, String matchId, boolean isVictory) {
        combinationService.saveCombination(duoRecord, matchId, isVictory);
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
