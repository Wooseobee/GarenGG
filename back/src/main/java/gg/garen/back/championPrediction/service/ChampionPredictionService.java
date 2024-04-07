package gg.garen.back.championPrediction.service;

import com.google.gson.Gson;
import gg.garen.back.champion.dto.ChampionDto;
import gg.garen.back.champion.entity.Champion;
import gg.garen.back.champion.repository.ChampionRepository;
import gg.garen.back.championPrediction.dto.ResponseGetChampionPredictionStartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChampionPredictionService {

    private final ChampionRepository championRepository;

    public ResponseEntity<?> getChampionPredictionStart() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

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

        // 대칭키 생성
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // AES 키 크기 설정
        SecretKey secretKey = keyGenerator.generateKey();

        // Cipher 객체 초기화
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // rounds 객체를 JSON으로 변환 (예시로 Gson 사용)
        Gson gson = new Gson();
        String roundsJson = gson.toJson(newRounds);

        // 데이터 암호화
        byte[] encryptedBytes = cipher.doFinal(roundsJson.getBytes());
        String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);

        responseGetChampionPredictionStartDto.setEncryptedData(encryptedData);

        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        responseGetChampionPredictionStartDto.setSecretKey(encodedKey);

        return ResponseEntity.status(HttpStatus.OK).body(responseGetChampionPredictionStartDto);
    }
}
