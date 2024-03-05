package org.example.getusermatches.controller;

import lombok.RequiredArgsConstructor;
import org.example.getusermatches.service.UserMatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserMatchController {

    private final UserMatchService userMatchService;

    @GetMapping("/test/{tier}/{rankNum}")
    public String test(@PathVariable("tier") String tier, @PathVariable("tier") String rankNum) throws InterruptedException {
        userMatchService.getUser(tier, rankNum);
        return "OK";
    }
}
