package org.example.apikeycrawling.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeagueListDto {
    private List<LeagueItemDTO> entries;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LeagueItemDTO{
        private String summonerId;
    }
}
