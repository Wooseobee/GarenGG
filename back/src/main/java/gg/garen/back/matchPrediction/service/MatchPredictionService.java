package gg.garen.back.matchPrediction.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gg.garen.back.common.domain.mongo.MatchInfo;
import gg.garen.back.common.domain.mongo.Participant;
import gg.garen.back.common.repository.UserMatchRepository;
import gg.garen.back.matchPrediction.dto.ParticipantDto;
import gg.garen.back.matchPrediction.dto.RandomMatchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchPredictionService {

    private final UserMatchRepository userMatchRepository;

    public RandomMatchResponseDto getRandomMatch(SecretKey secretKey1, SecretKey secretKey2) throws Exception {
        MatchInfo matchInfo = userMatchRepository.findRandomMatchInfo().get(0);
        while (matchInfo.getInfo().getGameDuration() < 900) {
            matchInfo = userMatchRepository.findRandomMatchInfo().get(0);
        }
        List<ParticipantDto> participants = new ArrayList<>();

        byte[] iv = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        for (Participant p : matchInfo.getInfo().getParticipants()) {
            String nickName;
            if (p.getRiotIdGameName() == null) {
                nickName = p.getSummonerName();
            } else {
                nickName = p.getRiotIdGameName();
            }
            participants.add(
                    ParticipantDto.builder()
                            .enemyMissingPings(encryptData(secretKey1, String.valueOf(p.getEnemyMissingPings()), iv))
                            .championName(encryptData(secretKey1, p.getChampionName(), iv))
                            .individualPosition(encryptData(secretKey1, p.getIndividualPosition(), iv))
                            .nickName(encryptData(secretKey1, nickName, iv))
                            .riotIdTagline(encryptData(secretKey1, p.getRiotIdTagline(), iv))
                            .kills(encryptData(secretKey1, String.valueOf(p.getKills()), iv))
                            .deaths(encryptData(secretKey1, String.valueOf(p.getDeaths()), iv))
                            .assists(encryptData(secretKey1,String.valueOf(p.getAssists()), iv))
                            .firstBloodKill(encryptData(secretKey1, String.valueOf(p.isFirstBloodKill()), iv))
                            .win(encryptData(secretKey1, String.valueOf(p.isWin()), iv))
                            .build());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] p = encryptData(secretKey2, objectMapper.writeValueAsString(participants), iv);

        byte[] key1 = secretKey1.getEncoded();
        byte[] key2 = secretKey2.getEncoded();
        byte[] amp = "&&".getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(key1.length + key2.length + amp.length);
        byteBuffer.put(key1);
        byteBuffer.put(amp);
        byteBuffer.put(key2);
        return RandomMatchResponseDto.builder()
                .matchId(encryptData(secretKey1, String.valueOf(matchInfo.getMatchId()), iv))
                .gameDuration(encryptData(secretKey1, String.valueOf(matchInfo.getInfo().getGameDuration()), iv))
                .participants(p)
                .matchInfo(byteBuffer.array())
                .match(iv)
                .tier(matchInfo.getTier())
                .build();
    }

    private byte[] encryptData(SecretKey secretKey, String data, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        return cipher.doFinal(data.getBytes());
    }
}
