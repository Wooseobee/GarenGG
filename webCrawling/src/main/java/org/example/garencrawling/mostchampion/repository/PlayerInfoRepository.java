package org.example.garencrawling.mostchampion.repository;

import org.example.garencrawling.mostchampion.domain.PlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerInfoRepository extends JpaRepository<PlayerInfo, Integer> {
}
