package org.example.getmatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getmatches.domain.Choice;
import org.example.getmatches.domain.mongo.Info;
import org.example.getmatches.domain.mongo.MatchInfo;
import org.example.getmatches.domain.mongo.Participant;
import org.example.getmatches.repository.UserMatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetMatchService {

    private final UserMatchRepository userMatchRepository;
    private final AsyncService asyncService;

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

            victoryChampions.sort(Comparator.comparing(Choice::getChampionName));
            defeatChampions.sort(Comparator.comparing(Choice::getChampionName));

            asyncService.processCombinations(victoryChampions, matchInfo.getMatchId(), true);
            asyncService.processCombinations(defeatChampions, matchInfo.getMatchId(), false);

            successData++;
        }
        return successData;
    }

}
