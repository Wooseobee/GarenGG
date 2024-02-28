package com.example.riotApiCrawling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RiotApiCrawlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiotApiCrawlingApplication.class, args);
	}

}
