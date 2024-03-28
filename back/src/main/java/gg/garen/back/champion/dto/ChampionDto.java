package gg.garen.back.champion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChampionDto {
    Long championKey;
    String id;
    String name;
}
