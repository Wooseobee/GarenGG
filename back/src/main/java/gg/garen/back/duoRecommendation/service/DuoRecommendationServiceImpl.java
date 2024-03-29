package gg.garen.back.duoRecommendation.service;

import gg.garen.back.champion.entity.Champion;
import gg.garen.back.champion.service.ChampionUtils;
import gg.garen.back.duoRecommendation.dto.DuoRecommendationDto;
import gg.garen.back.duoRecommendation.entity.DuoRecord;
import gg.garen.back.duoRecommendation.repository.DuoRecommendationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
// @Primary
@Slf4j
@RequiredArgsConstructor
public class DuoRecommendationServiceImpl implements DuoRecommendationService{

    private final ChampionUtils championUtils;
    private final DuoRecommendationRepository duoRepository;
    private static final String[] positions = {"TOP", "JUNGLE", "MIDDLE", "BOTTOM", "UTILITY"};
    private Map<String, Long> championNameKeyMap; //요청을 이름으로받아서 키로 탐색
    private Map<Long, String> championKeyNameMap; //키를 이름으로 바꿔서 응답.
    private Map<Long, String> championKeyIdMap; ///키를 영어이름으로 바꿔줌.

    private Map<String, Long> getChampionNameKeyMap(){
        if(championNameKeyMap == null){
            championNameKeyMap = new HashMap<>();
            List<Champion> champions = championUtils.getChampions();
            for (Champion c : champions) {
                championNameKeyMap.put(c.getName(), c.getKey());
            }
        }
        return championNameKeyMap;
    }

    private Map<Long, String> getChampionKeyNameMap(){
        if(championKeyNameMap == null){
            championKeyNameMap = new HashMap<>();
            List<Champion> champions = championUtils.getChampions();
            for (Champion c : champions) {
                championKeyNameMap.put(c.getKey(), c.getName());
            }
        }
        return championKeyNameMap;
    }

    private Map<Long, String> getChampionKeyIdMap(){
        if(championKeyIdMap == null){
            championKeyIdMap = new HashMap<>();
            List<Champion> champions = championUtils.getChampions();
            for (Champion c : champions) {
                championKeyIdMap.put(c.getKey(), c.getId());
            }
        }
        return championKeyIdMap;
    }

    public List<DuoRecommendationDto> duoRecommend(String curChampion, String curPosition) {
        log.info("Service : function duoRecommend called : {} {}", curChampion, curPosition);

        long curChampionKey = getChampionNameKeyMap().get(curChampion);
        log.info("Service : curChampionKey : {}",curChampionKey);
        //입력받은 챔피언의 모든 전적 가져오기
        System.out.println(curChampionKey + " , " + curPosition);
        List<DuoRecord> duoRecords = duoRepository.findDuoRecordByChampionName(curChampionKey, curPosition);
        log.info("Service : get DuoRecordList : {}", duoRecords);
        int[] positionCount = new int[5];
        //포지션별로 가져오기. 포지션별로 최대3개까지만 가져오기
        List<DuoRecommendationDto> duoRecommendationDtos = new ArrayList<>();
        for(DuoRecord duoRecord : duoRecords){
            //포지션별로 3개씩만 받게하기 고민할 것.
            String position = duoRecord.getLane1().equals(curPosition)? duoRecord.getLane2() : duoRecord.getLane1();
            Long championKey =  duoRecord.getChampion1() == curChampionKey? duoRecord.getChampion2() : duoRecord.getChampion1();
            String champion = getChampionKeyNameMap().get(championKey);
            String id = getChampionKeyIdMap().get(championKey);

            int positionNum = -1;
            for(int i = 0; i < 5; i++){
                if(position.equals(positions[i])){
                    positionNum = i;
                    break;
                }
            }

            if(++positionCount[positionNum] > 3) continue;

            DuoRecommendationDto duoRecommendationDto = new DuoRecommendationDto();
            duoRecommendationDto.setChampion(champion);
            duoRecommendationDto.setPosition(position);
            duoRecommendationDto.setId(id);

            duoRecommendationDtos.add(duoRecommendationDto);

        }

        if(duoRecommendationDtos.size() == 0)
            return null;
        else
            return duoRecommendationDtos;
    }
}
