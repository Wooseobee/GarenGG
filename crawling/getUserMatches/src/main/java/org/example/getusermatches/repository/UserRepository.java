package org.example.getusermatches.repository;

import org.example.getusermatches.domain.PlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<PlayerInfo, String> {

    List<PlayerInfo> findAllByTierAndRank(String tier, String rank);
}
