package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/models").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/model").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/model/add").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/namespace").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/statements").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/statements/add").allowedOrigins("http://localhost:3000");
            }
        };
    }

}
