package org.example.getusermatches.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.getusermatches.domain.ApiKey;
import org.example.getusermatches.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
public class APIKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private List<String> apiKeys;

    @PostConstruct
    private void initApiKeys() {
        apiKeys = apiKeyRepository.findAll().stream()
                .map(ApiKey::getApiKey)
                .collect(Collectors.toList());
    }

}
