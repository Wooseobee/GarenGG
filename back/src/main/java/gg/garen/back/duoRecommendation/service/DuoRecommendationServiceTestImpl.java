package gg.garen.back.duoRecommendation.service;

import gg.garen.back.duoRecommendation.dto.DuoRecommendationDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class DuoRecommendationServiceTestImpl implements DuoRecommendationService{

    @Override
    public List<DuoRecommendationDto> duoRecommend(String curChampion, String curPosition) {
        List<DuoRecommendationDto> test = new ArrayList<>();
        test.add(new DuoRecommendationDto("갈리오", "JUNGLE", "Galio"));
        test.add(new DuoRecommendationDto("갈리오", "JUNGLE", "Galio"));
        test.add(new DuoRecommendationDto("갈리오", "JUNGLE", "Galio"));

        test.add(new DuoRecommendationDto("갈리오","MIDDLE","Annie"));
        test.add(new DuoRecommendationDto("갈리오","MIDDLE","Annie"));
        test.add(new DuoRecommendationDto("갈리오","MIDDLE","Annie"));

        test.add(new DuoRecommendationDto("트리스타나","BOTTOM","Tristana"));
        test.add(new DuoRecommendationDto("트리스타나","BOTTOM","Tristana"));
        test.add(new DuoRecommendationDto("트리스타나","BOTTOM","Tristana"));

        test.add(new DuoRecommendationDto("누누","UTILITY","Nunu"));
        test.add(new DuoRecommendationDto("누누","UTILITY","Nunu"));
        test.add(new DuoRecommendationDto("누누","UTILITY","Nunu"));

        return test;
    }
}
