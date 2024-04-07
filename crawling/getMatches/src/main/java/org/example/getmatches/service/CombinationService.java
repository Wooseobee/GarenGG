package org.example.getmatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getmatches.domain.mysql.DuoRecord;
import org.example.getmatches.domain.mysql.DuoRecordMatch;
import org.example.getmatches.domain.mysql.DuoRecordMatchKey;
import org.example.getmatches.repository.DuoRecordMatchRepository;
import org.example.getmatches.repository.DuoRecordRepository;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CombinationService {
    private final DuoRecordRepository duoRecordRepository;
    private final DuoRecordMatchRepository duoRecordMatchRepository;

    @Transactional(rollbackFor = Exception.class)
    public void saveCombination(DuoRecord duoRecord, String matchId, boolean isVictory) throws CannotAcquireLockException {
        DuoRecord foundDuoRecord = duoRecordRepository.findByWithPessimisticLock(duoRecord.getChampion1(), duoRecord.getLane1(), duoRecord.getChampion2(), duoRecord.getLane2());

        if (foundDuoRecord == null) {
            if (isVictory) {
                duoRecord.setVictory(1L);
            } else {
                duoRecord.setDefeat(1L);
            }
            duoRecord.setTotalMatch(1L);
            duoRecord.setWinRate(isVictory ? 100.0 : 0.0);
            DuoRecord savedDuoRecord = duoRecordRepository.save(duoRecord);

            saveCombinationMatch(savedDuoRecord, matchId);
            return;
        }
        if (duoRecordMatchRepository.findById(new DuoRecordMatchKey(foundDuoRecord.getId(), matchId)).isEmpty()) {
            updateAndSaveFoundCombination(foundDuoRecord, isVictory);
            saveCombinationMatch(foundDuoRecord, matchId);
        }
//        else {
//            log.info("already calculated match: matchId={}, combinationId={}", matchId, foundDuoRecord.getId());
//        }
    }

    private void saveCombinationMatch(DuoRecord duoRecord, String matchId) {
        DuoRecordMatchKey duoRecordMatchKey = new DuoRecordMatchKey(duoRecord.getId(), matchId);
        DuoRecordMatch duoRecordMatch = new DuoRecordMatch(duoRecordMatchKey, duoRecord);
        duoRecordMatchRepository.save(duoRecordMatch);
    }

    private void updateAndSaveFoundCombination(DuoRecord foundDuoRecord, boolean isVictory) {
        if (isVictory) {
            foundDuoRecord.setVictory(foundDuoRecord.getVictory() + 1);
        } else {
            foundDuoRecord.setDefeat(foundDuoRecord.getDefeat() + 1);
        }
        foundDuoRecord.setTotalMatch(foundDuoRecord.getTotalMatch() + 1);
        updateWinRate(foundDuoRecord);
        duoRecordRepository.save(foundDuoRecord);
    }

    private void updateWinRate(DuoRecord duoRecord) {
        long totalGames = duoRecord.getVictory() + duoRecord.getDefeat();
        if (totalGames > 0) {
            duoRecord.setWinRate((double) duoRecord.getVictory() / totalGames * 100.0);
        }
    }
}
