package org.example.garencrawling.mostchampion.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "player_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerInfo {
    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Column(name = "summoner_name")
    private String summonerName;

    @Column(name = "tag_line")
    private String tagLine;

    private String tier;

    @Column(name = "rank_num")
    private String rankNum;

    private String userNickname;
}
