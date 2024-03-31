package org.example.apikeycrawling.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.apikeycrawling.entity.mysql.PlayerInfoTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PlayerInfoTestCustomRepositoryImpl implements PlayerInfoTestCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveOrUpdate(List<PlayerInfoTest> playerInfoTests) {
        for (PlayerInfoTest test : playerInfoTests) {
            entityManager.createNativeQuery("INSERT INTO player_info_test (api_key_id, summoner_id, tier, rank_num, puuid, summoner_name, tag_line) VALUES (:apiKeyId, :summonerId, :tier, :rankNum, :puuid, :summonerName, :tagLine) ON DUPLICATE KEY UPDATE api_key_id = VALUES(api_key_id), summoner_id = VALUES(summoner_id), tier = VALUES(tier), rank_num = VALUES(rank_num), puuid = VALUES(puuid)")
                    .setParameter("apiKeyId", test.getApiKeyId())
                    .setParameter("summonerId", test.getSummonerId())
                    .setParameter("tier", test.getTier())
                    .setParameter("rankNum", test.getRankNum())
                    .setParameter("puuid", test.getPuuid())
                    .setParameter("summonerName", test.getSummonerName())
                    .setParameter("tagLine", test.getTagLine())
                    .executeUpdate();
        }
    }
}
