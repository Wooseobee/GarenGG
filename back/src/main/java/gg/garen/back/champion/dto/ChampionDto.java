package gg.garen.back.champion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChampionDto {
    String id;
    String name;
    Long championKey;
}
