package gg.garen.back.common.repository;

import gg.garen.back.common.domain.mysql.PlayerInfoTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerInfoTestRepository extends JpaRepository<PlayerInfoTest, Integer> {
    PlayerInfoTest findBySummonerNameAndTagLine(String summonerName, String tagLine);
}
