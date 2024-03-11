package org.example.getmatches.service;

import lombok.RequiredArgsConstructor;
import org.example.getmatches.domain.mysql.Champion;
import org.example.getmatches.domain.mysql.Image;
import org.example.getmatches.domain.mysql.Info;
import org.example.getmatches.domain.mysql.Stats;
import org.example.getmatches.repository.ChampionRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RenewChampionService {

    private final ChampionRepository championRepository;

    @Transactional
    public void renewChampion(String version) {
        String url = "https://ddragon.leagueoflegends.com/cdn/" + version + "/data/ko_KR/champion.json";
        RestTemplate restTemplate = new RestTemplate();

        try {
            String resultJson = restTemplate.getForObject(url, String.class);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(resultJson);
            JSONObject jsonData = (JSONObject) jsonObject.get("data");

            List<Champion> championList = new ArrayList<>();

            for (Object o : jsonData.keySet()) {
                String championName = (String) o;
                championList.add(makeChampion((JSONObject) jsonData.get(championName)));
            }

            championRepository.saveAll(championList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Champion makeChampion(JSONObject c) {
        JSONObject getInfo = (JSONObject) c.get("info");
        Info info = Info.builder()
                .attack((Long) getInfo.get("attack"))
                .defense((Long) getInfo.get("defense"))
                .magic((Long) getInfo.get("magic"))
                .difficulty((Long) getInfo.get("difficulty"))
                .build();
        JSONObject getImage = (JSONObject) c.get("image");
        Image image = Image.builder()
                .full((String) getImage.get("full"))
                .sprite((String) getImage.get("sprite"))
                .group((String) getImage.get("group"))
                .x((Long) getImage.get("x"))
                .y((Long) getImage.get("Y"))
                .w((Long) getImage.get("w"))
                .h((Long) getImage.get("h"))
                .build();

        JSONArray getTags = (JSONArray) c.get("tags");
        List<String> tags = new ArrayList<>();
        for (Object getTag : getTags) {
            tags.add((String) getTag);
        }
        JSONObject getStats = (JSONObject) c.get("stats");
        Stats stats = Stats.builder()
                .hp(((Number) getStats.get("hp")).doubleValue())
                .hpperlevel(((Number) getStats.get("hpperlevel")).doubleValue())
                .mp(((Number) getStats.get("mp")).doubleValue())
                .mpperlevel(((Number) getStats.get("mpperlevel")).doubleValue())
                .movespeed(((Number) getStats.get("movespeed")).doubleValue())
                .armor(((Number) getStats.get("armor")).doubleValue())
                .armorperlevel(((Number) getStats.get("armorperlevel")).doubleValue())
                .spellblock(((Number) getStats.get("spellblock")).doubleValue())
                .spellblockperlevel(((Number) getStats.get("spellblockperlevel")).doubleValue())
                .attackrange(((Number) getStats.get("attackrange")).doubleValue())
                .hpregen(((Number) getStats.get("hpregen")).doubleValue())
                .hpregenperlevel(((Number) getStats.get("hpregenperlevel")).doubleValue())
                .mpregen(((Number) getStats.get("mpregen")).doubleValue())
                .mpregenperlevel(((Number) getStats.get("mpregenperlevel")).doubleValue())
                .crit(((Number) getStats.get("crit")).doubleValue())
                .critperlevel(((Number) getStats.get("critperlevel")).doubleValue())
                .attackdamage(((Number) getStats.get("attackdamage")).doubleValue())
                .attackdamageperlevel(((Number) getStats.get("attackdamageperlevel")).doubleValue())
                .attackspeedperlevel(((Number) getStats.get("attackspeedperlevel")).doubleValue())
                .attackspeed(((Number) getStats.get("attackspeed")).doubleValue())
                .build();

        return Champion.builder()
                .key(Long.valueOf(c.get("key").toString()))
                .version((String) c.get("version"))
                .id((String) c.get("id"))
                .name((String) c.get("name"))
                .title((String) c.get("title"))
                .blurb((String) c.get("blurb"))
                .info(info)
                .image(image)
                .tags(tags)
                .partype((String) c.get("partype"))
                .stats(stats)
                .build();
    }
}
