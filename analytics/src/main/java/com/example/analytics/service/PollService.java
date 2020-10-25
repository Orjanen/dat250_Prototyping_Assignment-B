package com.example.analytics.service;

import com.example.analytics.io.entity.PollEntity;
import com.example.analytics.io.repository.PollRepository;
import com.example.analytics.shared.dto.PollDto;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollService {

    @Autowired
    PollRepository pollRepository;

    ModelMapper modelMapper = new ModelMapper();

    public List<PollDto> getAllPolls() {
        System.out.println("Getting all polls...");
        List<PollEntity> polls = pollRepository.findAll();
        System.out.println("Done");
        return polls.stream().map(poll -> modelMapper.map(poll, PollDto.class)).collect(Collectors.toList());
    }

    public PollDto getPollById(String id) {
        System.out.println("Getting poll with ID: " + id);
        PollEntity pollEntity = pollRepository.findById(id).orElse(null);
        if (pollEntity == null) throw new ResourceNotFoundException("pollEntity is null");
        System.out.println("Done");
        return modelMapper.map(pollEntity, PollDto.class);
    }

    public PollDto createPoll(PollDto pollDto) {
        System.out.println("Creating a poll...");

        PollEntity pollEntity = new PollEntity();//modelMapper.map(pollDto, PollEntity.class);
        pollEntity.setJpaId(pollDto.getJpaId());
        pollEntity.setPollName(pollDto.getPollName());
        pollEntity.setOptionOne(pollDto.getOptionOne());
        pollEntity.setOptionTwo(pollDto.getOptionTwo());
        pollEntity.setOptionOneVotes(0);
        pollEntity.setOptionTwoVotes(0);
        pollEntity.setStillActive(true);

        PollEntity storedPollDetails = pollRepository.save(pollEntity);
        System.out.println("Done");
        return modelMapper.map(storedPollDetails, PollDto.class);
    }

    public PollDto getPollByJpaId(String jpaId) {
        System.out.println("Getting poll with JPA-ID: " + jpaId);
        PollEntity pollEntity = pollRepository.findByJpaId(jpaId);
        if (pollEntity == null) throw new ResourceNotFoundException("pollEntity is null");
        System.out.println("Done");
        return modelMapper.map(pollEntity, PollDto.class);
    }

    public PollDto updatePollVotes(PollDto newVotes, String jpaId) {
        System.out.println("Starting poll-update on poll with JPA-ID: " + jpaId);
        PollEntity pollToUpdate = modelMapper.map(getPollByJpaId(jpaId), PollEntity.class);
        if (pollToUpdate == null) throw new ResourceNotFoundException("pollEntity is null");

        System.out.println("Updating votes...");
        pollToUpdate.setOptionOneVotes(pollToUpdate.getOptionOneVotes() + newVotes.getOptionOneVotes());
        pollToUpdate.setOptionTwoVotes(pollToUpdate.getOptionTwoVotes() + newVotes.getOptionTwoVotes());

        PollEntity storedPollDetails = pollRepository.save(pollToUpdate);
        System.out.println("Done");
        return modelMapper.map(storedPollDetails, PollDto.class);
    }


}
