package gg.garen.back.common.repository;

import gg.garen.back.common.domain.mysql.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
