package gg.garen.back.champion.service;

import gg.garen.back.champion.dto.ChampionDto;
import gg.garen.back.champion.entity.Champion;
import gg.garen.back.champion.repository.ChampionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChampionService {


    private final ChampionRepository championRepository;

    List<ChampionDto> list = new ArrayList<>();

    @PostConstruct
    public void init(){
        List<Champion> champions = championRepository.findAll();

        this.list = champions.stream()
                .map((champion -> new ChampionDto(champion.getId(), champion.getName())))
                .collect(Collectors.toList());
    }
    public List<ChampionDto> championInfo(){
        return list;
    }
}
