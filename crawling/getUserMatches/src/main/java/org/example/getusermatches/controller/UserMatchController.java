package org.example.getusermatches.controller;

import lombok.RequiredArgsConstructor;
import org.example.getusermatches.service.UserMatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserMatchController {

    private final UserMatchService userMatchService;

    @GetMapping("/match/{index}/{startTime}/{endTime}")
    public String match(@PathVariable("index") int index,
                        @PathVariable("startTime") String startTime,
                        @PathVariable("endTime") String endTime) throws InterruptedException {
        userMatchService.saveUserMatch(index, startTime, endTime);
        return "OK";
    }
}
