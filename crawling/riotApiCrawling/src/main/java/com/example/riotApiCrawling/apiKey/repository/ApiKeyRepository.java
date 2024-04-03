package com.example.riotApiCrawling.apiKey.repository;

import com.example.riotApiCrawling.apiKey.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
}