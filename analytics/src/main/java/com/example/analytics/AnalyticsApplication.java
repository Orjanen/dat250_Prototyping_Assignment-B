package com.example.analytics;

import com.example.analytics.handler.JsonHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AnalyticsApplication {

    @Bean
    public JsonHandler jsonHandler() {
        return new JsonHandler();
    }


    public static void main(String[] args) {
        SpringApplication.run(AnalyticsApplication.class, args);
    }

}

