package gg.garen.back.common.repository;

import gg.garen.back.common.domain.mysql.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    List<Ranking> findTop10ByGameId(Long gameId);
}
