package com.emarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.List;

@Configuration(proxyBeanMethods = false)
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("Access-Control-Allow-Origin");
        config.addAllowedHeader(HttpHeaders.CONTENT_TYPE);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "HEAD", "DELETE", "OPTIONS"));
        config.setAllowedOrigins(List.of("localhost:5173", "127.0.0.1:5173"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}