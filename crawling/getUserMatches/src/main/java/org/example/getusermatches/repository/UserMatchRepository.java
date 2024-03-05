package org.example.getusermatches.repository;

import org.example.getusermatches.domain.MatchInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMatchRepository extends MongoRepository<MatchInfo, String>, CustomMatchInfoRepository {
}
