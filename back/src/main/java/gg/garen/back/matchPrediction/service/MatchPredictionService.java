package gg.garen.back.matchPrediction.service;

import gg.garen.back.common.domain.mongo.MatchInfo;
import gg.garen.back.common.domain.mongo.Participant;
import gg.garen.back.common.repository.UserMatchRepository;
import gg.garen.back.matchPrediction.dto.ParticipantDto;
import gg.garen.back.matchPrediction.dto.RandomMatchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchPredictionService {

    private final UserMatchRepository userMatchRepository;

    @Transactional
    public RandomMatchResponseDto getRandomMatch(SecretKey secretKey) throws Exception {
        MatchInfo matchInfo = userMatchRepository.findRandomMatchInfo().get(0);

        List<ParticipantDto> participants = new ArrayList<>();

        byte[] iv = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        for (Participant p : matchInfo.getInfo().getParticipants()) {
            participants.add(
                    ParticipantDto.builder()
                            .enemyMissingPings(encryptData(secretKey, p.getEnemyMissingPings(), iv))
                            .championName(encryptData(secretKey, p.getChampionName(), iv))
                            .individualPosition(encryptData(secretKey, p.getIndividualPosition(), iv))
                            .summonerName(encryptData(secretKey, p.getSummonerName(),iv))
                            .riotIdTagline(encryptData(secretKey, p.getRiotIdTagline(),iv))
                            .kills(encryptData(secretKey, p.getKills(), iv))
                            .deaths(encryptData(secretKey, p.getDeaths(),iv))
                            .firstBloodKill(encryptData(secretKey, p.isFirstBloodKill(),iv))
                            .win(encryptData(secretKey, p.isWin(),iv))
                            .build());
        }

        return RandomMatchResponseDto.builder()
                .matchId(matchInfo.getMatchId())
                .gameDuration(matchInfo.getInfo().getGameDuration())
                .gameVersion(matchInfo.getInfo().getGameVersion())
                .participants(participants)
                .secretKey(secretKey.getEncoded())
                .iv(iv)
                .build();
    }

    private byte[] encryptData(SecretKey secretKey, String data, byte[] iv) throws Exception {
        byte[] inputData = data.getBytes();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        return cipher.doFinal(inputData);
    }

    private byte[] encryptData(SecretKey secretKey, int data, byte[] iv) throws Exception {
        byte[] inputData = Integer.toString(data).getBytes();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        return cipher.doFinal(inputData);
    }

    private byte[] encryptData(SecretKey secretKey, boolean data, byte[] iv) throws Exception {
        byte[] inputData = String.valueOf(data).getBytes();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        return cipher.doFinal(inputData);
    }
}
