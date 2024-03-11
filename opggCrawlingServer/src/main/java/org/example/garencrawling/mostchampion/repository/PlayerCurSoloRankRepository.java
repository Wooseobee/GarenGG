package org.example.garencrawling.mostchampion.repository;

import org.example.garencrawling.mostchampion.domain.PlayerCurSoloRank;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerCurSoloRankRepository extends MongoRepository<PlayerCurSoloRank, Integer> {
}
