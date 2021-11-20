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
                registry.addMapping("/api/resources").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/resource/{name}").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/resource/add").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/resource/{id}").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/namespace").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/namespace/{name}").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/namespace/{id}").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/namespace/add").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/statement").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/statement/{id}").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/statement/add").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/statements").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/statements/{id}").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/statements/add").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/model/{id}/statements").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/verbs").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/verb/{verb}").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/verb/{id}").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/verb/add").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/properties").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/properties/{statementId}").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/property/{id}").allowedOrigins("http://localhost:3000");
                registry.addMapping("/api/statement/{id}/property/add").allowedOrigins("http://localhost:3000");
            }
        };
    }

}
