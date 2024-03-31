package org.example.apikeycrawling.component;

import lombok.RequiredArgsConstructor;
import org.example.apikeycrawling.dto.AccountDto;
import org.example.apikeycrawling.dto.LeagueEntryDto;
import org.example.apikeycrawling.dto.LeagueListDto;
import org.example.apikeycrawling.dto.SummonerDto;
import org.example.apikeycrawling.entity.mysql.PlayerInfoTest;
import org.example.apikeycrawling.global.GlobalConstants;
import org.example.apikeycrawling.repository.PlayerInfoTestRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PlayerInfoTestAsyncService {

    private final PlayerInfoTestRepository playerInfoTestRepository;

    @Async("threadPoolTaskExecutor")
    public void crawlingLowTier(String lowTier, String division, int startPage) {
        StringBuilder sb;
        int apiKeysIndex = 0;
        int page = startPage;

        while (true) {
            try {

                // 한번 던져보기
                String url;
                url = "https://kr.api.riotgames.com/lol/league/v4/entries/RANKED_SOLO_5x5/" + lowTier + "/" + division + "?page=" + page + "&api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                apiKeysIndex = (apiKeysIndex + 1) % GlobalConstants.apiKeys.size();
                List<LeagueEntryDto> leagueEntryDtos = GlobalConstants.restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<LeagueEntryDto>>() {
                }).getBody();

                // 종료
                if (leagueEntryDtos.isEmpty()) {
                    sb = new StringBuilder();
                    sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + lowTier + " " + division + " " + page + " 종료");
                    System.out.println(sb);
                    break;
                }

                // 한명씩 조사하기
                ArrayList<PlayerInfoTest> playerInfoTests = new ArrayList<>();
                List<CompletableFuture<Void>> futures = new ArrayList<>();
                for (int threadNumber = 0; threadNumber < leagueEntryDtos.size(); threadNumber++) {
                    CompletableFuture<Void> future = crawlingLowTierOnePerson(apiKeysIndex, threadNumber, lowTier, division, page, playerInfoTests);
                    apiKeysIndex = (apiKeysIndex + 1) % GlobalConstants.apiKeys.size();
                    futures.add(future);
                }
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

                // 모두 저장
                playerInfoTestRepository.saveOrUpdate(playerInfoTests);
                page++;

            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    sb = new StringBuilder();
                    sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + lowTier + " " + division + " " + page).append(" TOO_MANY_REQUESTS");
                    System.out.println(sb);
                } else {
                    sb = new StringBuilder();
                    sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + lowTier + " " + division + " " + page).append(" 이상한 버그1");
                    System.out.println(sb);
                }
            } catch (Exception e) {
                sb = new StringBuilder();
                sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + lowTier + " " + division + " " + page).append(" 이상한 버그2");
                System.out.println(sb);
            }
        }
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Void> crawlingLowTierOnePerson(int apiKeysIndex, int threadNumber, String lowTier, String division, int page, ArrayList<PlayerInfoTest> playerInfoTests) {

        return CompletableFuture.runAsync(() -> {

            StringBuilder sb;
            String url;
            List<LeagueEntryDto> leagueEntryDtos;
            SummonerDto summonerDto;
            AccountDto accountDto;

            while (true) {
                try {
                    url = "https://kr.api.riotgames.com/lol/league/v4/entries/RANKED_SOLO_5x5/" + lowTier + "/" + division + "?page=" + page + "&api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                    leagueEntryDtos = GlobalConstants.restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<LeagueEntryDto>>() {
                            })
                            .getBody();
                    url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/" + leagueEntryDtos.get(threadNumber).getSummonerId() + "?api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                    summonerDto = GlobalConstants.restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<SummonerDto>() {
                            })
                            .getBody();
                    url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + summonerDto.getPuuid() + "?api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                    accountDto = GlobalConstants.restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<AccountDto>() {
                            })
                            .getBody();

                    PlayerInfoTest playerInfoTest = new PlayerInfoTest();
                    playerInfoTest.setApiKeyId(String.valueOf(apiKeysIndex + 1));
                    playerInfoTest.setPuuid(summonerDto.getPuuid());
                    playerInfoTest.setRankNum(leagueEntryDtos.get(threadNumber).getRank());
                    playerInfoTest.setSummonerId(leagueEntryDtos.get(threadNumber).getSummonerId());
                    playerInfoTest.setSummonerName(accountDto.getGameName());
                    playerInfoTest.setTagLine(accountDto.getTagLine());
                    playerInfoTest.setTier(leagueEntryDtos.get(threadNumber).getTier());

                    playerInfoTests.add(playerInfoTest);
                    break;
                } catch (HttpClientErrorException e) {
                    if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                        sb = new StringBuilder();
                        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + lowTier + " " + division + " " + page).append(" TOO_MANY_REQUESTS");
                        System.out.println(sb);
                    } else {
                        sb = new StringBuilder();
                        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + lowTier + " " + division + " " + page).append(" 이상한 버그3");
                        System.out.println(sb);
                        break;
                    }
                } catch (Exception e) {
                    sb = new StringBuilder();
                    sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + lowTier + " " + division + " " + page).append(" 이상한 버그4");
                    System.out.println(sb);
                    break;
                }
            }
        });
    }

    @Async("threadPoolTaskExecutor")
    public void crawlingHighTier(String highTier) {
        StringBuilder sb;
        int apiKeysIndex = 0;

        while (true) {
            try {

                // 한번 던져보기
                String url = null;
                if (highTier.equals("CHALLENGER")) {
                    url = "https://kr.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                } else if (highTier.equals("GRANDMASTER")) {
                    url = "https://kr.api.riotgames.com/lol/league/v4/grandmasterleagues/by-queue/RANKED_SOLO_5x5?api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                } else if (highTier.equals("MASTER")) {
                    url = "https://kr.api.riotgames.com/lol/league/v4/masterleagues/by-queue/RANKED_SOLO_5x5?api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                }
                apiKeysIndex = (apiKeysIndex + 1) % GlobalConstants.apiKeys.size();
                LeagueListDto leagueListDto = GlobalConstants.restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<LeagueListDto>() {
                }).getBody();

                int chunkSize = 205;
                // 청크의 수 계산
                int numberOfChunks = (int) Math.ceil(leagueListDto.getEntries().size() / (double) chunkSize);

                for (int chunk = 0; chunk < numberOfChunks; chunk++) {
                    ArrayList<PlayerInfoTest> playerInfoTests = new ArrayList<>();
                    List<CompletableFuture<Void>> futures = new ArrayList<>();

                    // 현재 청크의 시작과 끝 인덱스 계산
                    int start = chunk * chunkSize;
                    int end = Math.min(start + chunkSize, leagueListDto.getEntries().size());

                    // 현재 청크의 각 항목 처리
                    for (int i = start; i < end; i++) {
                        CompletableFuture<Void> future = crawlingHighTierOnePerson(apiKeysIndex, i, highTier, playerInfoTests);
                        apiKeysIndex = (apiKeysIndex + 1) % GlobalConstants.apiKeys.size();
                        futures.add(future);
                    }

                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                    playerInfoTestRepository.saveOrUpdate(playerInfoTests);

                    sb = new StringBuilder();
                    sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" ").append(numberOfChunks).append(" ").append(chunk).append(" 완료");
                    System.out.println(sb);
                }

                break;

            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    sb = new StringBuilder();
                    sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + highTier + " TOO_MANY_REQUESTS");
                    System.out.println(sb);
                } else {
                    sb = new StringBuilder();
                    sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + highTier + " 이상한 버그1");
                    System.out.println(sb);
                }
            } catch (Exception e) {
                sb = new StringBuilder();
                sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + highTier + " 이상한 버그2");
                System.out.println(sb);
            }
        }

        sb = new StringBuilder();
        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + highTier + " 종료");
        System.out.println(sb);

    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Void> crawlingHighTierOnePerson(int apiKeysIndex, int threadNumber, String highTier, ArrayList<PlayerInfoTest> playerInfoTests) {

        return CompletableFuture.runAsync(() -> {

            StringBuilder sb;
            String url = null;
            SummonerDto summonerDto;
            AccountDto accountDto;

            while (true) {
                try {

                    if (highTier.equals("CHALLENGER")) {
                        url = "https://kr.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                    } else if (highTier.equals("GRANDMASTER")) {
                        url = "https://kr.api.riotgames.com/lol/league/v4/grandmasterleagues/by-queue/RANKED_SOLO_5x5?api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                    } else if (highTier.equals("MASTER")) {
                        url = "https://kr.api.riotgames.com/lol/league/v4/masterleagues/by-queue/RANKED_SOLO_5x5?api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                    }

                    LeagueListDto leagueListDto = GlobalConstants.restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<LeagueListDto>() {
                    }).getBody();

                    url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/" + leagueListDto.getEntries().get(threadNumber).getSummonerId() + "?api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                    summonerDto = GlobalConstants.restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<SummonerDto>() {
                    }).getBody();

                    url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-puuid/" + summonerDto.getPuuid() + "?api_key=" + GlobalConstants.apiKeys.get(apiKeysIndex).getApiKey();
                    accountDto = GlobalConstants.restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<AccountDto>() {
                    }).getBody();

                    PlayerInfoTest playerInfoTest = new PlayerInfoTest();
                    playerInfoTest.setApiKeyId(String.valueOf(apiKeysIndex + 1));
                    playerInfoTest.setPuuid(summonerDto.getPuuid());
                    playerInfoTest.setRankNum("I");
                    playerInfoTest.setSummonerId(leagueListDto.getEntries().get(threadNumber).getSummonerId());
                    playerInfoTest.setSummonerName(accountDto.getGameName());
                    playerInfoTest.setTagLine(accountDto.getTagLine());
                    playerInfoTest.setTier(highTier);

                    playerInfoTests.add(playerInfoTest);
                    break;
                } catch (HttpClientErrorException e) {
                    if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                        sb = new StringBuilder();
                        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + highTier + " TOO_MANY_REQUESTS");
                        System.out.println(sb);
                    } else {
                        sb = new StringBuilder();
                        sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + highTier + " 이상한 버그3");
                        System.out.println(sb);
                    }
                } catch (Exception e) {
                    sb = new StringBuilder();
                    sb.append("현재 시간: ").append(GlobalConstants.formatter.format(new Date())).append(" " + highTier + " 이상한 버그4");
                    System.out.println(sb);
                }
            }
        });
    }

}
