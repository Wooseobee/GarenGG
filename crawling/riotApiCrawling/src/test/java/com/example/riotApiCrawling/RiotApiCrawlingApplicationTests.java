package com.example.riotApiCrawling;

import com.example.riotApiCrawling.userCrawl.entity.PlayerInfo;
import com.example.riotApiCrawling.userCrawl.repository.UserRiotApiRepository;
import com.example.riotApiCrawling.userCrawl.service.UserRiotApiServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.Queue;

@SpringBootTest
class RiotApiCrawlingApplicationTests {
	@Autowired
	UserRiotApiRepository userRiotApiRepository;
	@Autowired
	UserRiotApiServiceImpl userRiotApiService;
	@Test
	void contextLoads() throws Exception{
//		userRiotApiService.crawlUsersByTier("DIAMOND","I",1,1, "RGAPI-6a511b87-ee81-4213-9b40-9b204acfaef2");
//        userRiotApiService.test();
		LinkedList<PlayerInfo> list = userRiotApiRepository.findByTierAndRank("CHALLENGER", "I");
		PlayerInfo pi = list.poll();

		userRiotApiService.fetchPuuid(pi);
	}

}
