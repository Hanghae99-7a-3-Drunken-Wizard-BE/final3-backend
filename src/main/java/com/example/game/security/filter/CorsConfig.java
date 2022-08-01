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
        config.addAllowedOrigin("http://drunken-wizard.com");
        config.addAllowedOrigin("https://drunken-wizard.com");
        config.addAllowedOrigin("https://mo-greene.shop");
//        config.addAllowedOrigin("https://mo-greene.shop/SufficientAmountOfAlcohol");
//        config.addAllowedOrigin("http://127.0.0.1:8000");
//        config.addAllowedOrigin("https://127.0.0.1:8000");
//        config.addAllowedOrigin("http://61.75.78.153");
//        config.addAllowedOrigin("https://61.75.78.153");
//        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("*");
        source.registerCorsConfiguration("/**",config);

        return new CorsFilter(source);
    }
}
