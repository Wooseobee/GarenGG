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
        test.add(new DuoRecommendationDto("갈리오", "jungle", "Galio"));
        test.add(new DuoRecommendationDto("갈리오", "jungle", "Galio"));
        test.add(new DuoRecommendationDto("갈리오", "jungle", "Galio"));

        test.add(new DuoRecommendationDto("갈리오","middle","Annie"));
        test.add(new DuoRecommendationDto("갈리오","middle","Annie"));
        test.add(new DuoRecommendationDto("갈리오","middle","Annie"));

        test.add(new DuoRecommendationDto("트리스타나","bottom","Tristana"));
        test.add(new DuoRecommendationDto("트리스타나","bottom","Tristana"));
        test.add(new DuoRecommendationDto("트리스타나","bottom","Tristana"));

        test.add(new DuoRecommendationDto("누누","utility","Nunu"));
        test.add(new DuoRecommendationDto("누누","utility","Nunu"));
        test.add(new DuoRecommendationDto("누누","utility","Nunu"));

        return test;
    }
}
