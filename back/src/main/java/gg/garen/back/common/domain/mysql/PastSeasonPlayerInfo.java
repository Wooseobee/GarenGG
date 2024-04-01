package gg.garen.back.common.domain.mysql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "past_season_player_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PastSeasonPlayerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    private String puuid;

    private String tier;

    @Column(columnDefinition = "VARCHAR(100) COLLATE utf8mb4_bin")
    private String tagLine;

    @Column(columnDefinition = "VARCHAR(100) COLLATE utf8mb4_bin")
    private String summonerName;

    private Long apiKeyId;
}
