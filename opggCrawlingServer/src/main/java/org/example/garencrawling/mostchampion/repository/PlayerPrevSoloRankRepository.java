package org.example.garencrawling.mostchampion.repository;

import org.example.garencrawling.mostchampion.domain.PlayerPrevSoloRank;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerPrevSoloRankRepository extends MongoRepository<PlayerPrevSoloRank, Integer> {

}