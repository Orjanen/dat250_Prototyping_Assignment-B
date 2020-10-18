package com.restdeveloper.app.ws.service.implementation;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.io.entity.VoteEntity;
import com.restdeveloper.app.ws.io.repository.PollRepository;
import com.restdeveloper.app.ws.io.repository.UserRepository;
import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.shared.Utils;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public PollDto createPoll(PollDto poll, String userId) {

        UserEntity user = userRepository.findByUserId(userId);

        if(user == null) throw new ResourceNotFoundException("User not found");

        PollEntity pollEntity = modelMapper.map(poll, PollEntity.class);
        pollEntity.setCreator(user);
        pollEntity.setPollId(utils.generatePollId(8));


        pollEntity.setStartTime(LocalDateTime.now());


        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setVoteId(utils.generateUserId(30));
        voteEntity.setPollEntity(pollEntity);
        //pollEntity.setVoteEntity(voteEntity);

        PollEntity storedPollDetails = pollRepository.save(pollEntity);
        PollDto returnValue = modelMapper.map(storedPollDetails, PollDto.class);

        return returnValue;
    }
    @Override
    public PollDto getPollByPollId(String id) {

        PollEntity pollEntity = pollRepository.findByPollId(id);
        if(pollEntity == null) throw new ResourceNotFoundException("pollEntity is null");
        PollDto returnValue = modelMapper.map(pollEntity, PollDto.class);

        return returnValue;
    }


    @Override
    public List<PollDto> getAllPollsByCreator(String userId) {

        UserEntity user = userRepository.findByUserId(userId);
        List<PollEntity> polls = pollRepository.findAllPollsByCreator(user);
        List<PollDto> returnValue = polls.stream().map(poll -> modelMapper.map(poll, PollDto.class)).collect(Collectors.toList());

        return returnValue;
    }
}
