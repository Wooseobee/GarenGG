package org.example.getusermatches.repository;

import lombok.RequiredArgsConstructor;
import org.example.getusermatches.domain.MatchInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomMatchInfoRepositoryImpl implements CustomMatchInfoRepository {

    private final MongoTemplate mongoTemplate;

    public void upsertMatchInfo(MatchInfo matchInfo) {
        Query query = new Query(Criteria.where("matchId").is(matchInfo.getMatchId()));
        Update update = new Update();

        update.set("info", matchInfo.getInfo());
        update.set("tier", matchInfo.getTier());
        update.set("rank", matchInfo.getRank());

        mongoTemplate.upsert(query, update, MatchInfo.class);
    }
}
