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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetMatchService {

    private final CombinationMatchRepository combinationMatchRepository;
    private final CombinationRepository combinationRepository;
    private final UserMatchRepository userMatchRepository;

    @Transactional
    public void saveMatchData(int offset) {
        Pageable pageable = PageRequest.of(offset, 1000);
        Page<MatchInfo> matchList = userMatchRepository.findAll(pageable);
        List<MatchInfo> content = matchList.getContent();
        for (MatchInfo matchInfo : content) {
            Info info = matchInfo.getInfo();

            List<Participant> participants = info.getParticipants();
            Combination victory = new Combination();
            Combination defeat = new Combination();

            for (Participant participant : participants) {
                int championId = participant.getChampionId();
                String individualPosition = participant.getIndividualPosition();
                boolean win = participant.isWin();

                switch (individualPosition) {
                    case "TOP":
                        if (win) {
                            victory.setTop(championId);
                        } else {
                            defeat.setTop(championId);
                        }
                        break;
                    case "JUNGLE":
                        if (win) {
                            victory.setJungle(championId);
                        } else {
                            defeat.setJungle(championId);
                        }
                        break;
                    case "MIDDLE":
                        if (win) {
                            victory.setMiddle(championId);
                        } else {
                            defeat.setMiddle(championId);
                        }
                        break;
                    case "BOTTOM":
                        if (win) {
                            victory.setBottom(championId);
                        } else {
                            defeat.setBottom(championId);
                        }
                        break;
                    case "UTILITY":
                        if (win) {
                            victory.setUtility(championId);
                        } else {
                            defeat.setUtility(championId);
                        }
                        break;
                    default:
                        break;
                }
            }

            if (!(isValidCombination(victory) && isValidCombination(defeat))) {
                continue;
            }
            saveVictory(victory, matchInfo.getMetadata().getMatchId());
            saveDefeat(defeat, matchInfo.getMetadata().getMatchId());
        }
    }

    private void saveVictory(Combination victory, String matchId) {
        Combination findVictoryCombination = combinationRepository.findByWithPessimisticLock(victory.getTop(), victory.getJungle(), victory.getMiddle(), victory.getBottom(), victory.getUtility());
        if (findVictoryCombination == null) {
            victory.setVictory(1L);
            Combination savedCombination = combinationRepository.save(victory);
            Long combinationId = savedCombination.getId();
            CombinationMatchKey combinationMatchKey = new CombinationMatchKey(combinationId, matchId);
            CombinationMatch combinationMatch = new CombinationMatch(combinationMatchKey, savedCombination);
            combinationMatchRepository.save(combinationMatch);
            return;
        }
        Long combinationId = findVictoryCombination.getId();

        // 이미 계산된 매치와 조합
        if (combinationMatchRepository.findById(new CombinationMatchKey(combinationId, matchId)).isPresent()) {
            log.info("already calculated match: matchId={}, combinationId={}", matchId, combinationId);
            return;
        }
        findVictoryCombination.setVictory(findVictoryCombination.getVictory() + 1);
        combinationRepository.save(findVictoryCombination);
    }

    private void saveDefeat(Combination defeat, String matchId) {
        Combination findDefeatCombination = combinationRepository.findByWithPessimisticLock(defeat.getTop(), defeat.getJungle(), defeat.getMiddle(), defeat.getBottom(), defeat.getUtility());
        if (findDefeatCombination == null) {
            defeat.setDefeat(1L);
            Combination savedCombination = combinationRepository.save(defeat);
            Long combinationId = savedCombination.getId();
            CombinationMatchKey combinationMatchKey = new CombinationMatchKey(combinationId, matchId);
            CombinationMatch combinationMatch = new CombinationMatch(combinationMatchKey, savedCombination);
            combinationMatchRepository.save(combinationMatch);
            return;
        }

        Long combinationId = findDefeatCombination.getId();

        // 이미 계산된 매치와 조합
        if (combinationMatchRepository.findById(new CombinationMatchKey(combinationId, matchId)).isEmpty()) {
            log.info("already calculated match: matchId={}, combinationId={}", matchId, combinationId);
            return;
        }
        findDefeatCombination.setDefeat(findDefeatCombination.getDefeat() + 1);
        combinationRepository.save(findDefeatCombination);
    }

    private boolean isValidCombination(Combination match) {
        return match.getTop() != null && match.getJungle() != null && match.getMiddle() != null && match.getBottom() != null && match.getUtility() != null;
    }
}
