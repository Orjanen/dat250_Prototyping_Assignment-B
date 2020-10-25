package com.example.analytics.ui.controller;

import com.example.analytics.io.entity.PollEntity;
import com.example.analytics.io.repository.PollRepository;
import com.example.analytics.ui.model.VoteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("poll")
public class MongodbController {


    @Autowired
    PollRepository pollRepository;

    @GetMapping
    public List<PollEntity> getAllPolls() {
        System.out.println("Getting all polls...");
        return pollRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public PollEntity getPollById(@PathVariable String id) {
        System.out.println("Getting poll with ID: " + id);
        PollEntity poll = pollRepository.findById(id).orElse(null);

        if (poll == null) {
            System.out.println("Could not find poll");
            return null;
        } else return poll;
    }

    @PostMapping
    public PollEntity createPoll(@RequestBody PollEntity poll) {
        System.out.println("Creating a poll...");
        return pollRepository.save(poll);
    }

    @DeleteMapping
    public String deleteAllPolls() {
        System.out.println("Deleting all polls...");
        pollRepository.deleteAll();
        return "Polls are deleted successfully";
    }

    @GetMapping(path = "/jpa-id/{jpaId}")
    public PollEntity getPollByJpaId(@PathVariable String jpaId) {
        System.out.println("Getting poll with JPA-ID: " + jpaId);
        PollEntity poll = pollRepository.findByJpaId(jpaId);

        if (poll == null) {
            System.out.println("Could not find poll");
            return null;
        } else return poll;
    }

    @PutMapping(path = "/jpa-id/{jpaId}")
    public PollEntity updatePollVotes(@RequestBody VoteModel vote, @PathVariable("jpaId") String jpaId){
        System.out.println("Starting poll-update on poll with JPA-ID: " + jpaId);
        PollEntity poll = pollRepository.findByJpaId(jpaId);
        if (poll == null) {
            System.out.println("Could not find poll, and therefore, could not update poll");
            return null;
        }

        System.out.println("Updating votes on poll with JPA-ID: " + jpaId);
        poll.setOptionOneVotes(poll.getOptionOneVotes() + vote.getOptionOneVotes());
        poll.setOptionTwoVotes(poll.getOptionTwoVotes() + vote.getOptionTwoVotes());

        return pollRepository.save(poll);
    }
}
