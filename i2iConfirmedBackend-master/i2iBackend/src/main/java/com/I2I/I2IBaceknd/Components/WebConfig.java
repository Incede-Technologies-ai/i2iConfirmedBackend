package com.I2I.I2IBaceknd.Components;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*") // For dev only, use specific origin in production
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(false);
    }
}
