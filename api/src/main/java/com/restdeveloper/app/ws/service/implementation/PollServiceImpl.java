package com.restdeveloper.app.ws.service.implementation;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.io.repository.PollRepository;
import com.restdeveloper.app.ws.io.repository.UserRepository;
import com.restdeveloper.app.ws.publisher.dweet_io.DweetIOAlerter;
import com.restdeveloper.app.ws.publisher.dweet_io.DweetStatusConstants;
import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.shared.Utils;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {

    @Autowired
    Utils utils;
    @Autowired
    PollRepository pollRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    DweetIOAlerter dweetIOAlerter;

    private static final Logger LOGGER = LoggerFactory.getLogger(PollServiceImpl.class);

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public PollDto createPoll(PollDto poll, String userId) {
        LOGGER.info("Creating poll, from user with user-ID: {}", userId);

        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            LOGGER.error("Could not find user with user-ID: {}", user);
            throw new ResourceNotFoundException("Could not find user with user-ID: " + userId);
        }

        PollEntity pollEntity = modelMapper.map(poll, PollEntity.class);
        pollEntity.setCreator(user);
        pollEntity.setPollId(utils.generatePollId(8));

        pollEntity.setStartTime(LocalDateTime.now());
        pollEntity.setEndTime(LocalDateTime.now().plus(poll.getDuration()));
        pollEntity.setAlertsHaveBeenSent(false);

        //Code still remaining from iteration 1 where users had one vote?
        //VoteEntity voteEntity = new VoteEntity()
        //voteEntity.setVoteId(utils.generateUserId(30))
        //voteEntity.setPollEntity(pollEntity)
        //pollEntity.setVoteEntity(voteEntity)

        PollEntity storedPollDetails = pollRepository.save(pollEntity);
        PollDto returnValue = modelMapper.map(storedPollDetails, PollDto.class);

        LOGGER.debug("Done creating poll");
        dweetIOAlerter.notifyDweetAboutPoll(pollEntity, DweetStatusConstants.POLL_STARTED);
        return returnValue;
    }

    @Override
    public PollDto getPollByPollId(String id) {
        LOGGER.info("Getting poll with ID: {}", id);

        PollEntity pollEntity = pollRepository.findByPollId(id);
        if (pollEntity == null) {
            LOGGER.error("Could not find poll with ID: {}", id);
            throw new ResourceNotFoundException("Could not find poll with ID: " + id);
        }
        PollDto returnValue = modelMapper.map(pollEntity, PollDto.class);

        LOGGER.debug("Done getting poll");
        return returnValue;
    }


    @Override
    public List<PollDto> getAllPollsByCreator(String userId) {
        LOGGER.info("Getting all polls created by user with user-ID: {}", userId);

        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) throw new ResourceNotFoundException("Could not find user: " + userId);

        List<PollEntity> polls = pollRepository.findAllPollsByCreator(user);
        List<PollDto> returnValue =
                polls.stream().map(poll -> modelMapper.map(poll, PollDto.class)).collect(Collectors.toList());

        LOGGER.debug("Done getting all polls from user");
        return returnValue;
    }

    @Override
    public void deletePoll(String pollId) {
        LOGGER.info("Deleting poll with poll-ID: {}", pollId);

        PollEntity pollEntity = pollRepository.findByPollId(pollId);
        if (pollEntity == null) throw new ResourceNotFoundException("pollEntity is null");

        pollRepository.delete(pollEntity);
        LOGGER.debug("Done deleting poll");
    }


    @Override
    public PollDto getCurrentPollStatusForWebSocket(String pollId) {
        LOGGER.info("Getting current status of poll with poll-ID: {}", pollId);

        PollEntity poll = pollRepository.findByPollId(pollId);
        if (poll == null) {
            LOGGER.error("Could not find poll with ID: {}", pollId);
            throw new ResourceNotFoundException("Could not find poll with ID: " + pollId);
        }

        PollDto pollDto = modelMapper.map(poll, PollDto.class);

        LOGGER.debug("Done getting current status");

        return pollDto;

    }

    @Override
    public List<PollDto> getAllPolls() {

        List<PollDto> pollDtos = new ArrayList<>();
        pollRepository.findAll().forEach(pollEntity -> pollDtos.add(modelMapper.map(pollEntity, PollDto.class)));

        return pollDtos;
    }

    @Override
    public boolean pollIsPrivate(String pollId) {
        boolean isPrivate = false;
        try {
            isPrivate = pollRepository.findByPollId(pollId).isPrivate();
        } catch (NullPointerException e) {
            return false;
        }
        return isPrivate;
    }

}
