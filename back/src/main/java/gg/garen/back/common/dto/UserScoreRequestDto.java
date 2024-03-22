package gg.garen.back.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserScoreRequestDto {
    private Long gameId;
    private String nickname;
    private int score;
}
