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
            String nickName;
            if (p.getRiotIdGameName() == null) {
                nickName = p.getSummonerName();
            } else {
                nickName = p.getRiotIdGameName();
            }
            participants.add(
                    ParticipantDto.builder()
                            .enemyMissingPings(encryptData(secretKey, String.valueOf(p.getEnemyMissingPings()), iv))
                            .championName(encryptData(secretKey, p.getChampionName(), iv))
                            .individualPosition(encryptData(secretKey, p.getIndividualPosition(), iv))
                            .nickName(encryptData(secretKey, nickName, iv))
                            .riotIdTagline(encryptData(secretKey, p.getRiotIdTagline(), iv))
                            .kills(encryptData(secretKey, String.valueOf(p.getKills()), iv))
                            .deaths(encryptData(secretKey, String.valueOf(p.getDeaths()), iv))
                            .firstBloodKill(encryptData(secretKey, String.valueOf(p.isFirstBloodKill()), iv))
                            .win(encryptData(secretKey, String.valueOf(p.isWin()), iv))
                            .build());
        }

        return RandomMatchResponseDto.builder()
                .matchId(matchInfo.getMatchId())
                .gameDuration(encryptData(secretKey, String.valueOf(matchInfo.getInfo().getGameDuration()), iv))
                .gameVersion(matchInfo.getInfo().getGameVersion())
                .participants(participants)
                .secretKey(secretKey.getEncoded())
                .iv(iv)
                .build();
    }

    private byte[] encryptData(SecretKey secretKey, String data, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        return cipher.doFinal(data.getBytes());
    }
}
