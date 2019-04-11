package com.lh.ekbase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    private static String[] originsVal = new String[]{
            "localhost:8888",
            "localhost:8080",
            "localhost:80",
            "localhost",
            "localhost:3000",
            "47.100.127.2:8080",
            "47.100.127.2:8000",
            "47.100.127.2:80",
            "47.100.127.2",
            "47.100.127.2:3000",
    };

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        addAllowedOrigins(corsConfiguration); // 1
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(true); // 跨域session共享
        source.registerCorsConfiguration("/**", corsConfiguration); // 4
        return new CorsFilter(source);
    }

    private void addAllowedOrigins(CorsConfiguration corsConfiguration) {
        for (String origin : originsVal) {
            corsConfiguration.addAllowedOrigin("http://" + origin);
            corsConfiguration.addAllowedOrigin("https://" + origin);
        }
    }
}

