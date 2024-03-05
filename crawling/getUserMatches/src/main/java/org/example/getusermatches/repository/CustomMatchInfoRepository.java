package org.example.getusermatches.repository;

import org.example.getusermatches.domain.MatchInfo;

public interface CustomMatchInfoRepository {
    void upsertMatchInfo(MatchInfo matchInfo);
}
