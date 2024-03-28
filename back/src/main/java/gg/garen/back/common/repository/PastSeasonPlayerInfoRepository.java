package gg.garen.back.common.repository;

import gg.garen.back.common.domain.mysql.PastSeasonPlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PastSeasonPlayerInfoRepository extends JpaRepository<PastSeasonPlayerInfo, Integer> {
    PastSeasonPlayerInfo findBySummonerNameAndTagLine(String summonerName, String tagLine);
}
