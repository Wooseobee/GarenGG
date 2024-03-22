package gg.garen.back.common.controller;

import gg.garen.back.common.dto.RankResponseDto;
import gg.garen.back.common.dto.SaveGameRequestDto;
import gg.garen.back.common.dto.UserScoreRequestDto;
import gg.garen.back.common.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/playGame")
    public List<RankResponseDto> playGame(@RequestBody UserScoreRequestDto requestDto) {
        return gameService.playGame(requestDto);
    }

    @PostMapping("/saveGame")
    public String saveGame(@RequestBody SaveGameRequestDto requestDto) {
        return gameService.saveGame(requestDto);
    }
}
