package org.example.getmatches;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GetMatchesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetMatchesApplication.class, args);
    }

}
