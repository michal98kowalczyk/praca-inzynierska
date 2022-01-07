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

                registry.addMapping("/api/statement/{subject}/{predicate}/{object}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/models").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/model").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/model/{id}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/model/add").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/sources").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/resources").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/resource/{name}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/resource/add").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/resource/{id}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/category").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/category/{name}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/category/{id}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/category/add").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/statement").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/statement/{id}/details").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/statement/{id}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/statement/add").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/statements").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/statements/{id}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/statements/add").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/model/{id}/statements").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/verbs").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/verb/{verb}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/verb/{id}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/verb/add").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/properties").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/properties/{statementId}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/property/{id}").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
                registry.addMapping("/api/statement/{id}/property/add").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
            }
        };
    }

}
