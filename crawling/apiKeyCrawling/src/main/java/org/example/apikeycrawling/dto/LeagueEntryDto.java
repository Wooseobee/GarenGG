package org.example.apikeycrawling.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LeagueEntryDto {
    private String summonerId;
    private String tier;
    private String rank;
}