package org.example.garencrawling.mostchampion.domain;

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
    private Integer playerId;

    private String puuid;

    private String tier;

    private String rank_num;
}
