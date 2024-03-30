package org.example.apikeycrawling.entity.mysql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "player_info_v0")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerInfoV0 {
    @Id
    private Integer playerId;

    private String tier;
    private String summonerName;
    private String tagLine;
}

