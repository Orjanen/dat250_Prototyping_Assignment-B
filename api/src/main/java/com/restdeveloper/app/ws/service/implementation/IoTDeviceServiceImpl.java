package com.restdeveloper.app.ws.service.implementation;

import com.restdeveloper.app.ws.io.entity.IoTDevice;
import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.VoteEntity;
import com.restdeveloper.app.ws.io.repository.IoTDeviceRepository;
import com.restdeveloper.app.ws.io.repository.PollRepository;
import com.restdeveloper.app.ws.io.repository.UserRepository;
import com.restdeveloper.app.ws.io.repository.VoteRepository;
import com.restdeveloper.app.ws.service.IoTDeviceService;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.dto.IoTDeviceDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IoTDeviceServiceImpl implements IoTDeviceService {


    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PollRepository pollRepository;

    @Autowired
    IoTDeviceRepository deviceRepository;

    @Autowired
    VoteService voteService;

    ModelMapper modelMapper = new ModelMapper();


    //TODO: Change to IotDTO?




    @Override
    public IoTDeviceDto getIoTDeviceByPublicDeviceId(String publicDeviceId) {
        IoTDevice device = deviceRepository.findByPublicDeviceId(publicDeviceId);

        return modelMapper.map(device, IoTDeviceDto.class);
    }

    @Override
    public String getPairedPoll(String publicDeviceId) {
        IoTDevice device = deviceRepository.findByPublicDeviceId(publicDeviceId);
        PollEntity pollEntity = device.getCurrentPoll();

        return pollEntity != null ? pollEntity.getPollId() : null;

    }

    @Override
    public IoTDeviceDto addNewDevice(String deviceId) {
        IoTDevice newDevice = new IoTDevice(deviceId);
        IoTDevice savedDevice = deviceRepository.save(newDevice);

        IoTDeviceDto deviceDto = modelMapper.map(savedDevice, IoTDeviceDto.class);

        return deviceDto;
    }

    @Override
    public VoteDto updateVoteForCurrentPoll(String deviceId, VoteDto voteDto) {
        IoTDevice device = deviceRepository.findByPublicDeviceId(deviceId);
        if(device == null) throw new ResourceNotFoundException("Device is not registered");

        VoteEntity voteEntity = voteRepository.findByVoterAndPollEntity(device, device.getCurrentPoll());
        if(voteEntity == null) throw new ResourceNotFoundException("Vote not found");

        voteEntity.addVotesToOption1(voteDto.getOption1Count());
        voteEntity.addVotesToOption2(voteDto.getOption2Count());

        VoteEntity updatedVote = voteRepository.save(voteEntity);
        return modelMapper.map(updatedVote, VoteDto.class);

    }

    @Override
    public IoTDeviceDto setPairedPoll(String deviceId, String pollId) {

        IoTDevice device = deviceRepository.findByPublicDeviceId(deviceId);
        if(device == null) throw new ResourceNotFoundException("Device " + deviceId + " is not registered");

        PollEntity pollEntity = pollRepository.findByPollId(pollId);
        if(pollEntity == null) throw new ResourceNotFoundException("Poll " + pollId + " not found");

        device.pairDeviceWithPoll(pollEntity);

        //TODO: Change if implementation is changed to not allow changing away from un-finished poll
        VoteEntity vote = voteRepository.findByVoterAndPollEntity(device, pollEntity);
        if(vote == null){
            vote = new VoteEntity();
            vote.setPollEntity(pollEntity);
            vote.setVoter(device);
            voteRepository.save(vote);
        }

        deviceRepository.save(device);

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);

        IoTDeviceDto updatedDevice = modelMapper.map(device, IoTDeviceDto.class);
        updatedDevice.setVotesForCurrentPoll(voteDto);

        return updatedDevice;

    }


}
