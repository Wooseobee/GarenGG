package org.example.apikeycrawling.dto;

import lombok.*;

import java.util.List;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {
    private InfoDto info;

    @Setter
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoDto{
        private List<ParticipantDto> participants;
    }

    @Setter
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParticipantDto{
        private String championName;
        private String riotIdGameName;
        private String riotIdTagline;
        private Boolean win;
    }

}
