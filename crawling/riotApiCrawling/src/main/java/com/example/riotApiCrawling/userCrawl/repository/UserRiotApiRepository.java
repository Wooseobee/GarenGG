package com.example.riotApiCrawling.userCrawl.repository;

import com.example.riotApiCrawling.userCrawl.entity.PlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRiotApiRepository extends JpaRepository<PlayerInfo, Integer> {
    Optional<Object> findByPuuid(String puuid);

    Optional<Object> findBySummonerNameAndTagLine(String summonerName, String tagLine);

//    List<PlayerInfo> findByTierAndRank(String platinum, String iv);
//
//    List<PlayerInfo> findByTier(String platinum);

    @Query(value = "select api_key from api_key", nativeQuery = true)
    List<String> getApiKeys();

   LinkedList<PlayerInfo> findByTierAndRank(String tier, String rank);
}
