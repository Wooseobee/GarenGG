package gg.garen.back.duoRecommendation.service;

import gg.garen.back.duoRecommendation.dto.DuoRecommendationDto;
import gg.garen.back.duoRecommendation.entity.DuoRecord;
import gg.garen.back.duoRecommendation.repository.DuoRecommendationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DuoRecommendationService {

    private final DuoRecommendationRepository duoRepository;
    private static String[] positions = {"TOP", "JUNGLE", "MIDDLE", "BOTTOM", "UTILITY"};
    private Map<String, Long> championNameKeyMap = new HashMap<>();
    private Map<Long, String> championKeyNameMap = new HashMap<>();

    @PostConstruct
    public void init(){
        List<Object[]> list = duoRepository.findAllNamesAndKeys();
        for (Object[] entry : list) {
            championNameKeyMap.put((String) entry[0], (Long) entry[1]);
            championKeyNameMap.put((Long) entry[1], (String) entry[0]);
        }
    }
    public List<DuoRecommendationDto> duoRecommend(String curChampion, String curPosition) {

        long curChampionKey = championNameKeyMap.get(curChampion);

        //입력받은 챔피언의 모든 전적 가져오기
        List<DuoRecord> duoRecords = duoRepository.findDuoRecordByChampionName(curChampionKey, curPosition);
        int[] positionCount = new int[5];
        //포지션별로 가져오기. 포지션별로 최대3개까지만 가져오기
        List<DuoRecommendationDto> duoRecommendationDtos = new ArrayList<>();
        for(DuoRecord duoRecord : duoRecords){
            //포지션별로 3개씩만 받게하기 고민할 것.
            String position = duoRecord.getLane1().equals(curPosition)? duoRecord.getLane2() : duoRecord.getLane1();
            Long championKey =  duoRecord.getChampion1() == curChampionKey? duoRecord.getChampion2() : duoRecord.getChampion1();
            String champion = championKeyNameMap.get(championKey);
            int positionNum = -1;
            for(int i = 0; i < 5; i++){
                if(position.equals(positions[i])) positionNum = i;
                break;
            }

            if(++positionCount[positionNum] > 3) continue;

            DuoRecommendationDto duoRecommendationDto = new DuoRecommendationDto();
            duoRecommendationDto.setChampion(champion);
            duoRecommendationDto.setPosition(position);

            duoRecommendationDtos.add(duoRecommendationDto);

        }

        if(duoRecommendationDtos.size() == 0)
            return null;
        else
            return duoRecommendationDtos;
    }
}
