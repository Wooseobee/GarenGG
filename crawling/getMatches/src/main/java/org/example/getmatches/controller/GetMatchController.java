package org.example.getmatches.controller;

import lombok.RequiredArgsConstructor;
import org.example.getmatches.service.GetMatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMatchController {

    private final GetMatchService getMatchService;

    @GetMapping("/match/{offset}")
    public String setMatch(@PathVariable int offset){
        getMatchService.saveMatchData(offset);
        return "ok";
    }
}
