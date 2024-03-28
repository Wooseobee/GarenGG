package gg.garen.back.common.repository;

import gg.garen.back.common.domain.mongo.PastSeasonPlayerMost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PastSeasonPlayerMostRepository extends MongoRepository<PastSeasonPlayerMost, Integer> {
}
