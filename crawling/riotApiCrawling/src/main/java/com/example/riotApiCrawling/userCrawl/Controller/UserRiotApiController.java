package com.example.riotApiCrawling.userCrawl.Controller;

import com.example.riotApiCrawling.userCrawl.dto.RequestDto;
import com.example.riotApiCrawling.userCrawl.service.UserRiotApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//유저정보 가져오게하기
@RestController("/")
public class UserRiotApiController {
    private final UserRiotApiService userRiotApiService;


    UserRiotApiController(UserRiotApiService userRiotApiService){
        this.userRiotApiService = userRiotApiService;
    }
    @PostMapping
    ResponseEntity<Void> crawlUser(@RequestBody RequestDto requestDto) throws Exception{
        userRiotApiService.crawlUser(requestDto);

        return ResponseEntity.ok().build();
    }

    //healthcheck
    @GetMapping
    String healthcheck(){
        return "hello";
    }
}
