package org.example.garencrawling.mostchampion.repository;

import org.example.garencrawling.mostchampion.domain.PlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerInfoRepository extends JpaRepository<PlayerInfo, Integer> {

    // startPlayerId와 endPlayerId 사이의 playerId를 가진 PlayerInfo 목록을 조회하는 메서드
    List<PlayerInfo> findByPlayerIdBetween(Long startPlayerId, Long endPlayerId);
}
