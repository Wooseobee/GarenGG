package com.example.riotApiCrawling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RiotApiCrawlingApplicationTests {
	@Autowired
	UserRiotApiService userRiotApiService;
	@Test
	void contextLoads() {
		userRiotApiService.test();
	}

}
