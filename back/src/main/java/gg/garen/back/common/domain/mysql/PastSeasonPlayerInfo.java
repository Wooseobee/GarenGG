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

    private String tagLine;

    private String summonerName;

    private Long apiKeyId;
}
