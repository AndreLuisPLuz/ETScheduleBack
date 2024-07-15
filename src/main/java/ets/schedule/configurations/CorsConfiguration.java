package ets.schedule.configurations;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfiguration implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods(
                "GET", "POST", "PUT", "PATCH", "DELETE", 
                "OPTIONS", "HEAD", "TRACE", "CONNECT"
            );
    }
}
