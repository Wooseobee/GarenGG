package com.example.riotApiCrawling;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

//import static com.example.riotApiCrawling.UserRiotApiService.crawlUser;

@SpringBootApplication
public class RiotApiCrawlingApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(RiotApiCrawlingApplication.class, args);
	}

}
