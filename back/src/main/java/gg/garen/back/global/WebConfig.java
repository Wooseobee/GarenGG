package gg.garen.back.global;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins(
                        "https://j10a605.p.ssafy.io",
//                        "http://localhost:5173",
                        "https://garengg.shop",
                        "https://www.garengg.shop"
                )
                .allowedMethods("GET", "POST", "OPTIONS");
    }
}