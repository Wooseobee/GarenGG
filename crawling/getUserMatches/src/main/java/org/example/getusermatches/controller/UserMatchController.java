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

    @GetMapping("/match/{tier}/{rankNum}")
    public String match(@PathVariable("tier") String tier, @PathVariable("rankNum") String rankNum) throws InterruptedException {
        userMatchService.getUser(tier, rankNum);
        return "OK";
    }
}
