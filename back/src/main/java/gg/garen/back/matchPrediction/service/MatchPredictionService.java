package gg.garen.back.matchPrediction.service;

import gg.garen.back.common.domain.mongo.MatchInfo;
import gg.garen.back.common.domain.mongo.Participant;
import gg.garen.back.common.repository.UserMatchRepository;
import gg.garen.back.matchPrediction.dto.ParticipantDto;
import gg.garen.back.matchPrediction.dto.RandomMatchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchPredictionService {

    private final UserMatchRepository userMatchRepository;

    @Transactional
    public RandomMatchResponseDto getRandomMatch() {
        MatchInfo matchInfo = userMatchRepository.findRandomMatchInfo().get(0);

        List<ParticipantDto> participants = new ArrayList<>();

        for (Participant p : matchInfo.getInfo().getParticipants()) {
            participants.add(
                    ParticipantDto.builder()
                            .assistMePings(p.getAssistMePings())
                            .championName(p.getChampionName())
                            .individualPosition(p.getIndividualPosition())
                            .summonerName(p.getSummonerName())
                            .riotIdTagline(p.getRiotIdTagline())
                            .kills(p.getKills())
                            .deaths(p.getDeaths())
                            .firstBloodKill(p.isFirstBloodKill())
                            .win(p.isWin())
                            .build());
        }

        return RandomMatchResponseDto.builder()
                .matchId(matchInfo.getMatchId())
                .gameDuration(matchInfo.getInfo().getGameDuration())
                .gameVersion(matchInfo.getInfo().getGameVersion())
                .participants(participants)
                .build();
    }
}
