package com.example.riotApiCrawling.userCrawl.controller;

import com.example.riotApiCrawling.userCrawl.dto.RequestDto;
import com.example.riotApiCrawling.userCrawl.service.UserRiotApiService;
import com.example.riotApiCrawling.userCrawl.service.UserRiotApiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//유저정보 가져오게하기
@RestController("/")
public class UserRiotApiController {
    private final UserRiotApiServiceImpl userRiotApiService;


    UserRiotApiController(UserRiotApiServiceImpl userRiotApiService){
        this.userRiotApiService = userRiotApiService;
    }
    @PostMapping("users")
    ResponseEntity<Void> crawlUser() throws Exception{
//        userRiotApiService.crawlUser(requestDto);

        userRiotApiService.setUsers();
        return ResponseEntity.ok().build();
    }

    //healthcheck
    @GetMapping("healthCheck")
    String healthcheck(){
        return "hello";
    }
}
