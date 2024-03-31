package org.example.apikeycrawling.repository;

import org.example.apikeycrawling.entity.mysql.PlayerInfoTest;

import java.util.List;

public interface PlayerInfoTestCustomRepository {
    void saveOrUpdate(List<PlayerInfoTest> playerInfoTests);
}
