package gg.garen.back.common.repository;

import gg.garen.back.common.domain.mongo.PlayerMost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerMostRepository extends MongoRepository<PlayerMost, Integer> {
}
