package gg.garen.back.duoRecommendation.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DuoRecommendationDto {
    String champion;
    String position;
    String id; // 이미지 검색용
}
