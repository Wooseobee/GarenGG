package com.example.riotApiCrawling;

import com.example.riotApiCrawling.userCrawl.service.UserRiotApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RiotApiCrawlingApplicationTests {
	@Autowired
	UserRiotApiService userRiotApiService;
	@Test
	void contextLoads() {
//		userRiotApiService.test();
	}

}
