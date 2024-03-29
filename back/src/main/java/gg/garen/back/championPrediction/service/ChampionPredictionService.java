package gg.garen.back.championPrediction.service;

import gg.garen.back.champion.dto.ChampionDto;
import gg.garen.back.champion.entity.Champion;
import gg.garen.back.champion.repository.ChampionRepository;
import gg.garen.back.championPrediction.dto.ResponseGetChampionPredictionStartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChampionPredictionService {

    private final ChampionRepository championRepository;

    public ResponseEntity<?> getChampionPredictionStart() {

        ResponseGetChampionPredictionStartDto responseGetChampionPredictionStartDto = new ResponseGetChampionPredictionStartDto();

        List<Champion> tmpChampions = championRepository.findAll();
        List<ChampionDto> champions = new ArrayList<>();
        List<ChampionDto> champions2 = new ArrayList<>();
        for (Champion champion : tmpChampions) {
            ChampionDto championDto = new ChampionDto();
            championDto.setChampionKey(champion.getKey());
            championDto.setId(champion.getId());
            championDto.setName(champion.getName());
            champions.add(championDto);

            championDto = new ChampionDto();
            championDto.setChampionKey(champion.getKey());
            championDto.setId(champion.getId());
            championDto.setName(champion.getName());
            champions2.add(championDto);
        }

        // champions 리스트에 있는 Champion 객체들의 순서를 무작위로 섞음
        Collections.shuffle(champions);

        List<ResponseGetChampionPredictionStartDto.Round> newRounds = new ArrayList<>();
        for (ChampionDto championDto : champions) {

            ResponseGetChampionPredictionStartDto.Round newRound = new ResponseGetChampionPredictionStartDto.Round();
            newRound.setAnswerChampion(championDto);

            newRound.setCandidateChampions(new ArrayList<>());
            newRound.getCandidateChampions().add(championDto);

            Collections.shuffle(champions2); // 먼저 전체 리스트를 섞습니다.

            int index = 0;
            for (int i = 0; i < 4; i++) { // 상위 4개의 champion을 선택합니다.
                if (champions2.get(index).getChampionKey().equals(championDto.getChampionKey())) {
                    i--;
                    index++;
                    continue;
                }
                newRound.getCandidateChampions().add(champions2.get(index));
                index++;
            }

            Collections.shuffle(newRound.getCandidateChampions());

            newRounds.add(newRound);
        }
        responseGetChampionPredictionStartDto.setRounds(newRounds);

        return ResponseEntity.status(HttpStatus.OK).body(responseGetChampionPredictionStartDto);
    }
}
