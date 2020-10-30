package com.example.analytics.service;

import com.example.analytics.io.entity.PollEntity;
import com.example.analytics.io.repository.PollRepository;
import com.example.analytics.shared.dto.PollDto;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollService {

    @Autowired
    PollRepository pollRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PollService.class);

    ModelMapper modelMapper = new ModelMapper();

    public List<PollDto> getAllPolls() {
        LOGGER.info("Getting all polls");
        List<PollEntity> polls = pollRepository.findAll();

        LOGGER.debug("Done getting all polls");
        return polls.stream().map(poll -> modelMapper.map(poll, PollDto.class)).collect(Collectors.toList());
    }

    public PollDto getPollById(String id) {
        LOGGER.info("Getting poll with ID: {}", id);
        PollEntity pollEntity = pollRepository.findById(id).orElse(null);
        if (pollEntity == null) {
            LOGGER.error("Could not find a poll with ID: {}", id);
            throw new ResourceNotFoundException("Could not find poll with ID: " + id);
        }

        LOGGER.debug("Done getting poll with ID {}", id);
        return modelMapper.map(pollEntity, PollDto.class);
    }

    public PollDto createPoll(PollDto pollDto) {
        LOGGER.info("Creating a poll: {}", pollDto);

        PollEntity pollEntity = new PollEntity();
        pollEntity.setJpaId(pollDto.getJpaId());
        pollEntity.setPollName(pollDto.getPollName());
        pollEntity.setOptionOne(pollDto.getOptionOne());
        pollEntity.setOptionTwo(pollDto.getOptionTwo());
        pollEntity.setOptionOneVotes(0);
        pollEntity.setOptionTwoVotes(0);
        pollEntity.setStillActive(true);

        PollEntity storedPollDetails = pollRepository.save(pollEntity);
        LOGGER.debug("Done creating a poll: {}", pollDto);
        return modelMapper.map(storedPollDetails, PollDto.class);
    }

    public PollDto getPollByJpaId(String jpaId) {
        LOGGER.info("Getting poll with JPA-ID: {}", jpaId);
        PollEntity pollEntity = pollRepository.findByJpaId(jpaId);
        if (pollEntity == null) {
            LOGGER.error("Could not find poll with jpaID: {}", jpaId);
            throw new ResourceNotFoundException("Could not find poll with jpaID: " + jpaId);
        }

        LOGGER.debug("Done getting poll with JPA-ID: {}", jpaId);
        return modelMapper.map(pollEntity, PollDto.class);
    }

    public PollDto updatePollVotes(PollDto newVotes, String jpaId) {
        LOGGER.info("Starting poll-update with JPA-ID {}, and vote: {}", jpaId, newVotes);

        PollEntity pollToUpdate = modelMapper.map(getPollByJpaId(jpaId), PollEntity.class);
        if (pollToUpdate == null) {
            LOGGER.error("Could not update with jpaID: {}", jpaId);
            throw new ResourceNotFoundException("Could not update with jpaID: " + jpaId);
        }

        pollToUpdate.setOptionOneVotes(pollToUpdate.getOptionOneVotes() + newVotes.getOptionOneVotes());
        pollToUpdate.setOptionTwoVotes(pollToUpdate.getOptionTwoVotes() + newVotes.getOptionTwoVotes());

        PollEntity storedPollDetails = pollRepository.save(pollToUpdate);
        LOGGER.debug("Done updating poll with JPA-ID: {}", jpaId);
        return modelMapper.map(storedPollDetails, PollDto.class);
    }

}
