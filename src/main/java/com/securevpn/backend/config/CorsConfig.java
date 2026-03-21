package com.securevpn.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * CORS Configuration.
 *
 * Android apps call the backend directly via Retrofit — they do not use
 * browser CORS in the traditional sense. However this config is needed for:
 *   - Swagger UI (browser-based testing)
 *   - Web admin panels or future web app
 *   - Any browser-based client
 *
 * Privacy note:
 *   Restrict origins in production to only your own domain.
 *   Never allow wildcard origins (*) in production.
 *
 * TODO: Before going live, replace allowedOrigins with your actual domain(s).
 * TODO: When adding an admin web panel, add its domain here explicitly.
 */
@Configuration
public class CorsConfig {

    // TODO: Set this to your production domain in application-prod.properties
    @Value("${app.cors.allowed-origins:*}")
    private String allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // TODO: Replace with specific origins in production
        // Example: config.setAllowedOrigins(List.of("https://your-domain.com"));
        config.setAllowedOriginPatterns(List.of("*"));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept",
                "X-Requested-With",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
