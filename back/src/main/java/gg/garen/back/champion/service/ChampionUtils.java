package gg.garen.back.champion.service;

import gg.garen.back.champion.dto.ChampionDto;
import gg.garen.back.champion.entity.Champion;
import gg.garen.back.champion.repository.ChampionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//이 서비스에서는 단순히 챔피언 정보만 제공하길 원한다.
@Service
@RequiredArgsConstructor
public class ChampionUtils {

    private final ChampionRepository championRepository;

    List<Champion> champions;

    public List<ChampionDto> championInfo(){
        return getChampions().stream()
                .map((champion -> new ChampionDto(champion.getKey(),champion.getId(), champion.getName())))
                .collect(Collectors.toList());
    }

    //챔피언 엔티티반환.
    public List<Champion> getChampions(){
        if(champions == null) champions = championRepository.findAll();
        return champions;
    }
}
