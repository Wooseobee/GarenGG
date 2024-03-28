package com.example.riotApiCrawling.userCrawl.service;

import com.example.riotApiCrawling.apiKey.entity.ApiKey;
import com.example.riotApiCrawling.apiKey.repository.ApiKeyRepository;
import com.example.riotApiCrawling.userCrawl.dto.PlayerInfoDto;
import com.example.riotApiCrawling.userCrawl.entity.PlayerInfo;
import com.example.riotApiCrawling.userCrawl.repository.UserRiotApiRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserRiotApiServiceImpl {
    public String[] tiers = {"CHALLENGER", "GRANDMASTER", "MASTER", "DIAMOND", "EMERALD", "PLATINUM", "GOLD", "SILVER", "BRONZE", "IRON"};
    public String[] ranks = {"", "I", "II", "III", "IV"};

    private final AsyncUserService asyncUserService;

    public void setUsers(int sign) throws InterruptedException{
        long startTime = System.currentTimeMillis();
        int startkeyIdx = 0;
        for(int tierIdx = 0; tierIdx < tiers.length; tierIdx++){
            int rankLength = ranks.length - 1;
            if (tierIdx <= 2) rankLength = 1;
            for(int rankIdx = 1; rankIdx <= rankLength; rankIdx++){
                if(sign == 1) {
                    asyncUserService.setAllSummonerId(tiers[tierIdx], ranks[rankIdx], startkeyIdx);
                    startkeyIdx += 161;
                }
//                else if(sign == 2){
//                    asyncUserService.setAllPuuid(tiers[tierIdx],ranks[rankIdx]);
//                }
//                else if(sign == 3 ){
//                    asyncUserService.setAllNameAndTag(tiers[tierIdx],ranks[rankIdx]);
//                }
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("유저 쌓기 끝: " + elapsedTime/1000 + " 초 걸림.");
    }


}
