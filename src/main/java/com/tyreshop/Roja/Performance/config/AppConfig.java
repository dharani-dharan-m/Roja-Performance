package com.tyreshop.Roja.Performance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * AppConfig - Application configuration class
 * Configures CORS and other application-level settings
 * Implements WebMvcConfigurer for Spring MVC customization
 * 
 * @author Roja Performance
 * @version 1.0
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    /**
     * Configure CORS (Cross-Origin Resource Sharing) settings
     * Enables API to be called from different origins
     * 
     * @param registry CorsRegistry for mapping CORS configurations
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}

