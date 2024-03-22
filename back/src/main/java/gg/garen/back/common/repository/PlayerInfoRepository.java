package gg.garen.back.common.repository;


import gg.garen.back.common.domain.mysql.PlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerInfoRepository extends JpaRepository<PlayerInfo, Integer> {

    PlayerInfo findBySummonerNameAndTagLine(String summonerName, String tagLine);

}
