package org.example.garencrawling.mostchampion.repository;

import org.example.garencrawling.mostchampion.domain.PlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerInfoRepository extends JpaRepository<PlayerInfo, Integer> {

    List<PlayerInfo> findByPlayerIdBetween(Long startPlayerId, Long endPlayerId);
}
