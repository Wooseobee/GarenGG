package gg.garen.back.common.service;

import gg.garen.back.common.domain.mysql.Game;
import gg.garen.back.common.domain.mysql.Ranking;
import gg.garen.back.common.dto.RankResponseDto;
import gg.garen.back.common.dto.SaveGameRequestDto;
import gg.garen.back.common.dto.UserScoreRequestDto;
import gg.garen.back.common.repository.GameRepository;
import gg.garen.back.common.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final RankingRepository rankingRepository;
    private final GameRepository gameRepository;

    @Transactional
    public List<RankResponseDto> playGame(UserScoreRequestDto requestDto) {
        Game game = gameRepository.findById(requestDto.getGameId()).orElseThrow(() -> new RuntimeException("Game not found with id: " + requestDto.getGameId()));

        Ranking newRanking = new Ranking();
        newRanking.setGame(game);
        newRanking.setNickname(requestDto.getNickname());
        newRanking.setUuid(requestDto.getUuid());
        newRanking.setScore(requestDto.getScore());
        rankingRepository.save(newRanking);
        return getRank(game);
    }

    public List<RankResponseDto> getRank(Game game) {
        List<Ranking> topRankings = rankingRepository.findTop10ByGameIdOrderByScoreDesc(game.getId());
        List<RankResponseDto> result = new ArrayList<>();

        for (Ranking ranking : topRankings) {
            if (ranking != null) {
                RankResponseDto rank = RankResponseDto.builder()
                        .gameName(game.getName())
                        .nickname(ranking.getNickname())
                        .uuid(ranking.getUuid())
                        .Score(ranking.getScore())
                        .createdAt(ranking.getCreatedAt())
                        .build();
                result.add(rank);
            }
        }
        return result;
    }

    @Transactional
    public String saveGame(SaveGameRequestDto requestDto) {
        Game game = new Game();
        game.setName(requestDto.getName());
        game.setDescription(requestDto.getDescription());
        gameRepository.save(game);
        return "ok";
    }
}
