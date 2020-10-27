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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(IoTDeviceServiceImpl.class);

    ModelMapper modelMapper = new ModelMapper();


    //TODO: Change to IotDTO?


    @Override
    public IoTDeviceDto getIoTDeviceByPublicDeviceId(String publicDeviceId) {
        LOGGER.info("Getting IoT-device with public device-ID: {}", publicDeviceId);
        IoTDevice device = deviceRepository.findByPublicDeviceId(publicDeviceId);

        LOGGER.debug("Done getting IoT-device");
        return modelMapper.map(device, IoTDeviceDto.class);
    }

    @Override
    public String getPairedPoll(String publicDeviceId) {
        LOGGER.info("Getting poll, paired with IoT-device with public device-ID: {}", publicDeviceId);
        IoTDevice device = deviceRepository.findByPublicDeviceId(publicDeviceId);
        PollEntity pollEntity = device.getCurrentPoll();

        LOGGER.debug("Done getting poll");
        return pollEntity != null ? pollEntity.getPollId() : null;

    }

    @Override
    public IoTDeviceDto addNewDevice(String deviceId) {
        LOGGER.info("Adding new IoT-device with device-ID: {}", deviceId);
        IoTDevice newDevice = new IoTDevice(deviceId);
        IoTDevice savedDevice = deviceRepository.save(newDevice);

        IoTDeviceDto deviceDto = modelMapper.map(savedDevice, IoTDeviceDto.class);

        LOGGER.debug("Done adding IoT-device");
        return deviceDto;
    }

    @Override
    public VoteDto updateVoteForCurrentPoll(String deviceId, VoteDto voteDto) {
        LOGGER.info("Updating votes on current poll, for IoT-device with device-ID: {}", deviceId);

        IoTDevice device = deviceRepository.findByPublicDeviceId(deviceId);
        if (device == null) {
            LOGGER.error("Could not find a registered device with device-ID: {}", deviceId);
            throw new ResourceNotFoundException("Could not find a registered device with device-ID: " + deviceId);
        }

        VoteEntity voteEntity = voteRepository.findByVoterAndPollEntity(device, device.getCurrentPoll());
        if (voteEntity == null) {
            LOGGER.error("Could not find vote on device-ID: {}", deviceId);
            throw new ResourceNotFoundException("Could not find vote on device-ID: " + deviceId);
        }

        voteEntity.addVotesToOption1(voteDto.getOption1Count());
        voteEntity.addVotesToOption2(voteDto.getOption2Count());

        VoteEntity updatedVote = voteRepository.save(voteEntity);

        LOGGER.debug("Done updating votes on current poll");
        return modelMapper.map(updatedVote, VoteDto.class);
    }

    @Override
    public IoTDeviceDto setPairedPoll(String deviceId, String pollId) {
        LOGGER.info("Pairing poll with poll-ID {}, with IoT-device with device-ID: {}", pollId, deviceId);

        IoTDevice device = deviceRepository.findByPublicDeviceId(deviceId);
        if (device == null) {
            LOGGER.error("Could not find a registered device with device-ID: {}", deviceId);
            throw new ResourceNotFoundException("Could not find a registered device with device-ID: " + deviceId);
        }

        PollEntity pollEntity = pollRepository.findByPollId(pollId);
        if (pollEntity == null) {
            LOGGER.error("Could not find poll with poll-ID: {}", pollId);
            throw new ResourceNotFoundException("Could not find poll with poll-ID: " + pollId);
        }
        device.pairDeviceWithPoll(pollEntity);

        //TODO: Change if implementation is changed to not allow changing away from un-finished poll
        VoteEntity vote = voteRepository.findByVoterAndPollEntity(device, pollEntity);
        if (vote == null) {
            vote = new VoteEntity();
            vote.setPollEntity(pollEntity);
            vote.setVoter(device);
            voteRepository.save(vote);
        }

        deviceRepository.save(device);

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);

        IoTDeviceDto updatedDevice = modelMapper.map(device, IoTDeviceDto.class);
        updatedDevice.setVotesForCurrentPoll(voteDto);

        LOGGER.debug("Done pairing poll with IoT-device");
        return updatedDevice;
    }

}
