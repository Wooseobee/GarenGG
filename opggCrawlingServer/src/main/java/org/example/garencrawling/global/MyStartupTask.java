package org.example.garencrawling.global;

import lombok.RequiredArgsConstructor;
import org.example.garencrawling.mostchampion.domain.Champion;
import org.example.garencrawling.mostchampion.repository.ChampionRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.example.garencrawling.global.GlobalConstants.*;

@Component
@RequiredArgsConstructor
public class MyStartupTask implements ApplicationRunner {

    private final ChampionRepository championRepository;

    @Override
    public void run(ApplicationArguments args) {
        threadSize = proxyAddress.length * 2;

        List<Champion> champions = championRepository.findAll();
        for (Champion champion : champions) {
            championNames.put(champion.getName(), champion.getId());
            championNames.put(champion.getId(), champion.getId());

            String tmp = champion.getId().toLowerCase();
            tmp = Character.toUpperCase(tmp.charAt(0)) + tmp.substring(1);
            championNames.put(tmp, champion.getId());
        }

        championNames.put("RenataGlasc", "Renata");
        championNames.put("Wukong", "MonkeyKing");
        championNames.put("Nunu&Willump", "Nunu");
    }
}
