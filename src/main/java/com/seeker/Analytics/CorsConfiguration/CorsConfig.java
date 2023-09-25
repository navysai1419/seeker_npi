package com.seeker.Analytics.CorsConfiguration;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow requests from specific origins
        configuration.addAllowedOrigin("http://localhost:4200"); // Replace with your frontend URL

        // Allow specific HTTP methods (e.g., GET, POST, PUT, DELETE)
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");

        // Allow specific headers (e.g., Authorization)
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("Content-Type");

        // Enable credentials (if needed)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return (CorsConfigurationSource) source;
    }
}

