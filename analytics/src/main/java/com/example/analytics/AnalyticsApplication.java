package com.example.analytics;

import com.example.analytics.mongodb.io.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyticsApplication {

     @Autowired
     private PollRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsApplication.class, args);
    }

}

