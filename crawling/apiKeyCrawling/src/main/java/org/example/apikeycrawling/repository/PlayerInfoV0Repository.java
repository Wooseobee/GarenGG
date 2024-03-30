package org.example.apikeycrawling.repository;

import org.example.apikeycrawling.entity.mysql.PlayerInfoV0;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerInfoV0Repository extends JpaRepository<PlayerInfoV0, Integer> {

    PlayerInfoV0 findBySummonerNameAndTagLine(String summonerName, String tagLine);
}
