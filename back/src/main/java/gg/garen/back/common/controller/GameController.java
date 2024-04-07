package gg.garen.back.common.controller;

import gg.garen.back.common.dto.RankResponseDto;
import gg.garen.back.common.dto.SaveGameRequestDto;
import gg.garen.back.common.dto.UserScoreRequestDto;
import gg.garen.back.common.service.GameService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://garengg.shop", "https://j10a605.p.ssafy.io/"})
@Slf4j
public class GameController {

    private final GameService gameService;

    @PostMapping("/playGame")
    public List<RankResponseDto> playGame(@RequestBody UserScoreRequestDto requestDto, HttpServletRequest request) {
        log.info("IP: {}, nickname: {}, uuid: {}, score: {}", request.getRemoteAddr(), requestDto.getNickname(), requestDto.getUuid(), requestDto.getScore());
        return gameService.playGame(requestDto);
    }

    @PostMapping("/saveGame")
    public String saveGame(@RequestBody SaveGameRequestDto requestDto) {
        return gameService.saveGame(requestDto);
    }
}
