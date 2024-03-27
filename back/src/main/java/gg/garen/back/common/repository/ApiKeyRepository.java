package gg.garen.back.common.repository;

import gg.garen.back.common.domain.mysql.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
}
