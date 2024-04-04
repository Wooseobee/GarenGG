package org.example.apikeycrawling.component;

import lombok.RequiredArgsConstructor;
import org.example.apikeycrawling.global.GlobalConstants;
import org.example.apikeycrawling.repository.ApiKeyRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerInfoTestCrawlingComponent {

    private final ApiKeyRepository apiKeyRepository;
    private final PlayerInfoTestAsyncService playerInfoTestAsyncService;

    public void crawlingLowTiers() {

        GlobalConstants.apiKeys = apiKeyRepository.findAll();

        for (String lowTier : GlobalConstants.lowTiers) {
            for (String division : GlobalConstants.divisions) {
                playerInfoTestAsyncService.crawlingLowTier(lowTier, division, 1);

            }
        }
    }

    public void crawlingHighTiers() {
        GlobalConstants.apiKeys = apiKeyRepository.findAll();

        for (String highTier : GlobalConstants.highTiers) {
            playerInfoTestAsyncService.crawlingHighTier(highTier);
        }
    }


}