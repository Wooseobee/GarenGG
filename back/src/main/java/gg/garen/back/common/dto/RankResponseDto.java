package gg.garen.back.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RankResponseDto {
    private String gameName;
    private String nickname;
    private String uuid;
    private int Score;
    private LocalDateTime createdAt;
}
