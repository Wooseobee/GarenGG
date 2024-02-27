package com.example.riotApiCrawling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import static com.example.riotApiCrawling.UserRiotApiService.crawlUser;

@SpringBootApplication
public class RiotApiCrawlingApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(RiotApiCrawlingApplication.class, args);
		crawlUser();
	}

}
