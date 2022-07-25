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
        CorsConfiguration config=new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");           // 파이어베이스 생성시 한줄더 추가
        config.addAllowedOrigin("http://62442.s3-website.ap-northeast-2.amazonaws.com");
        config.addAllowedOrigin("http://drunkenwizardtest.s3-website.ap-northeast-2.amazonaws.com/");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("*");
        source.registerCorsConfiguration("/**",config);

        return new CorsFilter(source);
    }

}
