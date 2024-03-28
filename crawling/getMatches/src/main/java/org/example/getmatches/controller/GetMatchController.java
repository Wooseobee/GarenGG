package org.example.getmatches.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getmatches.service.GetMatchService;
import org.example.getmatches.service.RenewChampionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class GetMatchController {

    private final GetMatchService getMatchService;
    private final RenewChampionService renewChampionService;

    @GetMapping("/combination/{offset}")
    public String setMatch(@PathVariable("offset") int offset) {
        int page = offset;
        Pageable pageable = PageRequest.of(page, 10);
        int successData;
        do {
            successData = getMatchService.saveMatchData(pageable);
            pageable = PageRequest.of(++page, 10);
            log.info("조합 저장 완료 - page:{} & successData:{}", page, successData);
        } while (successData > 0);
        return "ok";
    }

    @GetMapping("/champion/{version}")
    public String renewChampion(@PathVariable String version) {
        renewChampionService.renewChampion(version);
        return "ok";
    }
}
