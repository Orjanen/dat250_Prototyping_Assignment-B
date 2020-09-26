package com.restdeveloper.app.ws.service.implementation;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.io.repository.PollRepository;
import com.restdeveloper.app.ws.io.repository.UserRepository;
import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.shared.Utils;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        pollEntity.setUserDetails(user);
        pollEntity.setPollId(utils.generatePollId(30));

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
    public List<PollDto> getPolls(String userId) {
        List<PollDto> returnValue = new ArrayList<>();

        return returnValue;
    }
}
