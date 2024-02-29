package org.example.getmatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getmatches.domain.mongo.Info;
import org.example.getmatches.domain.mongo.MatchInfo;
import org.example.getmatches.domain.mongo.Participant;
import org.example.getmatches.domain.mysql.Match;
import org.example.getmatches.repository.MatchRepository;
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

    private final MatchRepository matchRepository;
    private final UserMatchRepository userMatchRepository;

    @Transactional
    public void saveMatchData(int offset) {
        Pageable pageable = PageRequest.of(offset, 100);
        Page<MatchInfo> matchList = userMatchRepository.findAll(pageable);
        List<MatchInfo> content = matchList.getContent();
        for (MatchInfo matchInfo : content) {
            Info info = matchInfo.getInfo();

            List<Participant> participants = info.getParticipants();
            Match victory = new Match();
            Match defeat = new Match();

            for (Participant participant : participants) {
                int championId = participant.getChampionId();
                String individualPosition = participant.getIndividualPosition();
                boolean win = participant.isWin();

                switch (individualPosition) {
                    case "top":
                        if (win) {
                            victory.setTop(championId);
                        } else {
                            defeat.setTop(championId);
                        }
                        break;
                    case "jungle":
                        if (win) {
                            victory.setJungle(championId);
                        } else {
                            defeat.setJungle(championId);
                        }
                        break;
                    case "middle":
                        if (win) {
                            victory.setMiddle(championId);
                        } else {
                            defeat.setMiddle(championId);
                        }
                        break;
                    case "bottom":
                        if (win) {
                            victory.setBottom(championId);
                        } else {
                            defeat.setBottom(championId);
                        }
                        break;
                    case "utility":
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

            setMatch(victory);
            setMatch(defeat);
        }
    }

    private void setMatch(Match victory) {
        Match findVictoryMatch = matchRepository.findByWithPessimisticLock(victory.getTop(), victory.getJungle(), victory.getMiddle(), victory.getBottom(), victory.getUtility());
        if (findVictoryMatch == null) {
            victory.setVictory(1L);
        } else {
            victory.setVictory(findVictoryMatch.getVictory() + 1);
        }
        matchRepository.save(victory);
    }
}
