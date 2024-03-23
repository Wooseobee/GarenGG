package org.example.apikeycrawling.apikeycrawling.repository;

import org.example.apikeycrawling.apikeycrawling.domain.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

}
