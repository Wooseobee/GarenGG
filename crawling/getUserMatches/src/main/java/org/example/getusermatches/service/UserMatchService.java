package org.example.getusermatches.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getusermatches.domain.PlayerInfo;
import org.example.getusermatches.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMatchService {

    private final UserRepository userRepository;
    private final SaveMatchService saveMatchService;
    private final APIKeyService apiKeyService;

    private List<String> API_KEY;

    public void saveUserMatch(int index, String startTime, String endTime) throws InterruptedException {
        API_KEY = apiKeyService.getApiKeys();
        int idx = index;
        Pageable pageable = PageRequest.of(idx, 205);
        Page<PlayerInfo> find = userRepository.findAll(pageable);
        while (!find.isEmpty()) {
            List<PlayerInfo> content = find.getContent();

            for (int i = 0; i < content.size(); i++) {
                PlayerInfo playerInfo = content.get(i);
                String puuid = playerInfo.getPuuid();
                if (puuid == null) continue;
                int apiKeyId = playerInfo.getApiKeyId();
                String apiKey = API_KEY.get(apiKeyId - 1);
                try {
                    saveMatchService.getMatches(apiKeyId - 1, puuid, apiKey, startTime, endTime, playerInfo.getTier(), playerInfo.getRank());
                } catch (Exception e) {
                    Thread.sleep(10000);
                    i--;
                }
            }

            pageable = PageRequest.of(++idx, 205);
            find = userRepository.findAll(pageable);
            log.info("{}명 완료 | idx : {}", idx * 205, idx);
        }

    }

}
