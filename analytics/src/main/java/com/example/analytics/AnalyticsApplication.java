package com.example.analytics;

import com.example.analytics.mongodb.io.entity.PollEntity;
import com.example.analytics.mongodb.io.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyticsApplication {//implements CommandLineRunner {

	@Autowired
	private PollRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsApplication.class, args);
	}
/*
	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		// save a couple of polls
		repository.save(new PollEntity("p1", "TestPoll", "Yes", "No", 0, 0));
		repository.save(new PollEntity("p2", "TestPoll2", "This", "That", 0, 0));

		// fetch all polls
		System.out.println("Polls found with findAll():");
		System.out.println("-------------------------------");
		for (PollEntity poll : repository.findAll()) {
			System.out.println(poll);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Poll found with findByPollName('TestPoll'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByPollName("TestPoll"));


	}

 */
}
