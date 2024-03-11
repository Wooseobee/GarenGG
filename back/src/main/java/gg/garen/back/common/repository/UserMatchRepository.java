package gg.garen.back.common.repository;

import gg.garen.back.common.domain.mongo.MatchInfo;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserMatchRepository extends MongoRepository<MatchInfo, String> {

    @Aggregation(pipeline = { "{$sample: { size: 1 }}" })
    List<MatchInfo> findRandomMatchInfo();
}
