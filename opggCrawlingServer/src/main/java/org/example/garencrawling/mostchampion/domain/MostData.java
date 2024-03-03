package org.example.garencrawling.mostchampion.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MostData {

    private String mostSeq;
    private String champion;
    private String game;
    private String rating;
    private String gold;
    private String creepScore;
    private String maxKills;
    private String maxDeaths;
    private String averageDamageDealt;
    private String averageDamageTaken;
    private String doubleKills;
    private String tripleKills;
    private String quadraKills;
    private String pentaKills;

}
