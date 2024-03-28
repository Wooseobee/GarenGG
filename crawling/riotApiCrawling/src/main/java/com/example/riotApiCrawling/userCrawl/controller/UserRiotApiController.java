package com.example.riotApiCrawling.userCrawl.controller;

import com.example.riotApiCrawling.userCrawl.dto.RequestDto;
import com.example.riotApiCrawling.userCrawl.service.UserRiotApiService;
import com.example.riotApiCrawling.userCrawl.service.UserRiotApiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//유저정보 가져오게하기
@RestController("/")
public class UserRiotApiController {
    private final UserRiotApiServiceImpl userRiotApiService;


    UserRiotApiController(UserRiotApiServiceImpl userRiotApiService){
        this.userRiotApiService = userRiotApiService;
    }
    @GetMapping("users")
    ResponseEntity<Void> crawlUser(@RequestParam Integer sign, @RequestParam String tier, @RequestParam int rank) throws Exception{
//        userRiotApiService.crawlUser(requestDto);

        userRiotApiService.setUsers(sign, tier, rank);
        return ResponseEntity.ok().build();
    }

    //healthcheck
    @GetMapping("healthCheck")
    String healthcheck(){
        return "hello";
    }
}
