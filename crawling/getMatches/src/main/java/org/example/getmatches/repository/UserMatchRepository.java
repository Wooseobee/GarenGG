package org.example.getmatches.repository;

import org.example.getmatches.domain.mongo.MatchInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMatchRepository extends MongoRepository<MatchInfo, String> {
}
