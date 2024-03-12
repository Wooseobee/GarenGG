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

        System.out.println("corePoolSize = " + corePoolSize);
        System.out.println("maxPoolSize = " + maxPoolSize);
        System.out.println("queueCapacity = " + queueCapacity);

        System.out.println("threadSize = " + threadSize);
        System.out.println("saveSize = " + saveSize);

        System.out.println("waitTime = " + waitTime);
        System.out.println("sleepTime = " + sleepTime);

        System.out.println("tryMaxCount = " + tryMaxCount);

        List<Champion> champions = championRepository.findAll();
        for (int i = 0; i < champions.size(); i++) {
            championNames.put(champions.get(i).getName(), champions.get(i).getId());
        }
    }
}