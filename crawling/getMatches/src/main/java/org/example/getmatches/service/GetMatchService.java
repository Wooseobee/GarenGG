package org.example.getmatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getmatches.domain.mongo.Info;
import org.example.getmatches.domain.mongo.MatchInfo;
import org.example.getmatches.domain.mongo.Participant;
import org.example.getmatches.domain.mysql.Combination;
import org.example.getmatches.domain.mysql.CombinationMatch;
import org.example.getmatches.domain.mysql.CombinationMatchKey;
import org.example.getmatches.repository.CombinationMatchRepository;
import org.example.getmatches.repository.CombinationRepository;
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

    private final CombinationMatchRepository combinationMatchRepository;
    private final CombinationRepository combinationRepository;
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
                Combination combination = createCombination(champions.get(i), champions.get(j));
                if (isValidCombination(combination)) {
                    saveCombination(combination, matchId, isVictory);
                }
            }
        }
    }

    private Combination createCombination(Choice champion1, Choice champion2) {
        Combination combination = new Combination();
        combination.setChampion1(champion1.championId);
        combination.setLane1(champion1.lane);
        combination.setChampion2(champion2.championId);
        combination.setLane2(champion2.lane);
        return combination;
    }

    private void saveCombination(Combination combination, String matchId, boolean isVictory) {
        Combination foundCombination = combinationRepository.findByWithPessimisticLock(combination.getChampion1(), combination.getLane1(), combination.getChampion2(), combination.getLane2());

        if (foundCombination == null) {
            if (isVictory) {
                combination.setVictory(1L);
            } else {
                combination.setDefeat(1L);
            }
            combination.setTotalMatch(1L);
            combination.setWinRate(isVictory ? 100.0 : 0.0);
            Combination savedCombination = combinationRepository.save(combination);

            saveCombinationMatch(savedCombination, matchId);
            return;
        }
        if (combinationMatchRepository.findById(new CombinationMatchKey(foundCombination.getId(), matchId)).isEmpty()) {
            updateAndSaveFoundCombination(foundCombination, isVictory);
            saveCombinationMatch(foundCombination, matchId);
        } else {
            log.info("already calculated match: matchId={}, combinationId={}", matchId, foundCombination.getId());
        }
    }

    private void saveCombinationMatch(Combination combination, String matchId) {
        CombinationMatchKey combinationMatchKey = new CombinationMatchKey(combination.getId(), matchId);
        CombinationMatch combinationMatch = new CombinationMatch(combinationMatchKey, combination);
        combinationMatchRepository.save(combinationMatch);
    }

    private void updateAndSaveFoundCombination(Combination foundCombination, boolean isVictory) {
        if (isVictory) {
            foundCombination.setVictory(foundCombination.getVictory() + 1);
        } else {
            foundCombination.setDefeat(foundCombination.getDefeat() + 1);
        }
        foundCombination.setTotalMatch(foundCombination.getTotalMatch() + 1);
        updateWinRate(foundCombination);
        combinationRepository.save(foundCombination);
    }

    private void updateWinRate(Combination combination) {
        long totalGames = combination.getVictory() + combination.getDefeat();
        if (totalGames > 0) {
            combination.setWinRate((double) combination.getVictory() / totalGames * 100.0);
        }
    }

    private boolean isValidCombination(Combination match) {
        return match.getChampion1() != null && match.getChampion2() != null && match.getLane1() != null && match.getLane2() != null;
    }
}
