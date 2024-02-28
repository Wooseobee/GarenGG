package com.example.riotApiCrawling.repository;

import com.example.riotApiCrawling.entity.PlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRiotApiRepository extends JpaRepository<PlayerInfo, Integer> {
    Optional<Object> findByPuuid(String puuid);
}
