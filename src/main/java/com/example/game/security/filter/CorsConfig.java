package com.example.game.security.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");           // 파이어베이스 생성시 한줄더 추가
        config.addAllowedOrigin("http://drunkenwizardtest.s3-website.ap-northeast-2.amazonaws.com");
        config.addAllowedOrigin("http://drunken-wizard.com.s3-website.ap-northeast-2.amazonaws.com");
        config.addAllowedOrigin("https://drunken-wizard-frontend-32c82zchp-kordobby.vercel.app/");  // 윤님 주소
        config.addAllowedOrigin("https://drunken-wizard.vercel.app");   // 윤님 주소
        config.addAllowedOrigin("https://drunken-wizard-iota.vercel.app");  // 정욱님 주소
        config.addAllowedOrigin("https://drunken-wizard.com");
        config.addAllowedOrigin("https://www.drunken-wizard.com");
        config.addAllowedOrigin("https://mo-greene.shop");
        config.addAllowedOrigin("http://127.0.0.1:8080");
        config.addAllowedOrigin("https://127.0.0.1:8081");
        config.addAllowedOrigin("https://127.0.0.1:8082");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("*");
        source.registerCorsConfiguration("/**",config);

        return new CorsFilter(source);
    }
}
