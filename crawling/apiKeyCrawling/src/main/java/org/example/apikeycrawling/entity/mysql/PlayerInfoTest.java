package org.example.apikeycrawling.entity.mysql;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player_info_test", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"summoner_name", "tag_line"})
})

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerInfoTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    private String apiKeyId;

    private String summonerId;
    private String tier;
    private String rankNum;

    private String puuid;

    @Column(columnDefinition = "VARCHAR(100) COLLATE utf8mb4_bin")
    private String summonerName;

    @Column(columnDefinition = "VARCHAR(100) COLLATE utf8mb4_bin")
    private String tagLine;

}