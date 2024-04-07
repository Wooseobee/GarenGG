package org.example.getusermatches.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "player_info_test")
public class PlayerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer playerId;

    @Column(name = "puuid", unique = true)
    String puuid;
    String tagLine;
    String leagueId;
    String queueType;
    String tier;

    @Column(name = "rankNum")
    String rank;

    String summonerId;
    String summonerName;
    int leaguePoints;
    int wins;
    int losses;
    int apiKeyId;
    boolean veteran;
    boolean inactive;
    boolean freshBlood;
    boolean hotStreak;
}
