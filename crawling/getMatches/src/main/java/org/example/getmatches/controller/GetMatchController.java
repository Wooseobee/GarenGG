package org.example.getmatches.controller;

import lombok.RequiredArgsConstructor;
import org.example.getmatches.service.GetMatchService;
import org.example.getmatches.service.RenewChampionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMatchController {

    private final GetMatchService getMatchService;
    private final RenewChampionService renewChampionService;

    @GetMapping("/match/{offset}")
    public String setMatch(@PathVariable int offset) {
        getMatchService.saveMatchData(offset);
        return "ok";
    }

    @GetMapping("/champion/{version}")
    public String renewChampion(@PathVariable String version) {
        renewChampionService.renewChampion(version);
        return "ok";
    }
}
