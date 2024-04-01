package org.example.getmatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getmatches.domain.mongo.Info;
import org.example.getmatches.domain.mongo.MatchInfo;
import org.example.getmatches.domain.mongo.Participant;
import org.example.getmatches.domain.mysql.DuoRecord;
import org.example.getmatches.domain.mysql.DuoRecordMatch;
import org.example.getmatches.domain.mysql.DuoRecordMatchKey;
import org.example.getmatches.repository.DuoRecordMatchRepository;
import org.example.getmatches.repository.DuoRecordRepository;
import org.example.getmatches.repository.UserMatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetMatchService {

    private final DuoRecordMatchRepository duoRecordMatchRepository;
    private final DuoRecordRepository duoRecordRepository;
    private final UserMatchRepository userMatchRepository;

    private static class Choice {
        String championName;
        Integer championId;
        String lane;

        public Choice(String championName, Integer championId, String lane) {
            this.championName = championName;
            this.championId = championId;
            this.lane = lane;
        }
    }

    @Transactional
    public int saveMatchData(Pageable pageable) {
        Page<MatchInfo> matchList = userMatchRepository.findAll(pageable);
        List<MatchInfo> content = matchList.getContent();
        int successData = 0;
        for (MatchInfo matchInfo : content) {
            Info info = matchInfo.getInfo();

            List<Participant> participants = info.getParticipants();
            List<Choice> victoryChampions = new ArrayList<>();
            List<Choice> defeatChampions = new ArrayList<>();

            for (Participant participant : participants) {
                int championId = participant.getChampionId();
                String championName = participant.getChampionName();
                String individualPosition = participant.getIndividualPosition();
                boolean win = participant.isWin();
                Choice choice = new Choice(championName, championId, individualPosition);
                if (win) {
                    victoryChampions.add(choice);
                } else {
                    defeatChampions.add(choice);
                }
            }

            victoryChampions.sort(Comparator.comparing(o -> o.championName));
            defeatChampions.sort(Comparator.comparing(o -> o.championName));

            processCombinations(victoryChampions, matchInfo.getMatchId(), true);
            processCombinations(defeatChampions, matchInfo.getMatchId(), false);

            successData++;
        }
        return successData;
    }

    private void processCombinations(List<Choice> champions, String matchId, boolean isVictory) {
        for (int i = 0; i < champions.size(); i++) {
            for (int j = i + 1; j < champions.size(); j++) {
                DuoRecord duoRecord = createCombination(champions.get(i), champions.get(j));
                if (isValidCombination(duoRecord)) {
                    saveCombination(duoRecord, matchId, isVictory);
                }
            }
        }
    }

    private DuoRecord createCombination(Choice champion1, Choice champion2) {
        DuoRecord duoRecord = new DuoRecord();
        duoRecord.setChampion1(Long.valueOf(champion1.championId));
        duoRecord.setLane1(champion1.lane);
        duoRecord.setChampion2(Long.valueOf(champion2.championId));
        duoRecord.setLane2(champion2.lane);
        return duoRecord;
    }

    private void saveCombination(DuoRecord duoRecord, String matchId, boolean isVictory) {
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
        } else {
            log.info("already calculated match: matchId={}, combinationId={}", matchId, foundDuoRecord.getId());
        }
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

    private boolean isValidCombination(DuoRecord match) {
        return match.getChampion1() != null && match.getChampion2() != null && match.getLane1() != null && match.getLane2() != null;
    }
}
