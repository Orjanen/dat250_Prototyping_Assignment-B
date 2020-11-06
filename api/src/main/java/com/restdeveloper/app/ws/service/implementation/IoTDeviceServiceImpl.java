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
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    private static final String IOT_DEVICE = "IoT device with public device-ID: ";

    ModelMapper modelMapper = new ModelMapper();



    @Override
    public IoTDeviceDto getIoTDeviceByPublicDeviceId(String publicDeviceId) {
        LOGGER.info("Getting IoT-device with public device-ID: {}", publicDeviceId);
        IoTDevice device = deviceRepository.findByPublicDeviceId(publicDeviceId);
        if (device == null)
            throw new ResourceNotFoundException(IOT_DEVICE + publicDeviceId + " could not be found!");

        LOGGER.debug("Done getting IoT-device");
        return modelMapper.map(device, IoTDeviceDto.class);
    }

    @Override
    public PollDto getPairedPoll(String publicDeviceId) {
        LOGGER.info("Getting poll, paired with IoT-device with public device-ID: {}", publicDeviceId);
        IoTDevice device = deviceRepository.findByPublicDeviceId(publicDeviceId);
        if (device == null)
            throw new ResourceNotFoundException(IOT_DEVICE + publicDeviceId + " could not be found!");

        PollEntity pollEntity = device.getCurrentPoll();
        if (pollEntity == null)
            throw new ResourceNotFoundException(IOT_DEVICE + publicDeviceId + " is not paired with a poll!");

        LOGGER.debug("Done getting poll");


        return modelMapper.map(pollEntity, PollDto.class);

    }

    @Override
    public IoTDeviceDto addNewDevice(IoTDeviceDto ioTDeviceDto) {
        LOGGER.info("Adding new IoT-device with device-ID: {}", ioTDeviceDto.getPublicDeviceId());

        //Modelmapper won't recognize the public id - resort to creating new entity
        IoTDevice newDevice = new IoTDevice(ioTDeviceDto.getPublicDeviceId());

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

        if (device.getCurrentPoll() == null) {
            throw new IllegalStateException("Device with device-ID : " + deviceId + " is not paired with a poll!");
        }

        VoteEntity voteEntity = findVoteForDeviceAndPoll(device, device.getCurrentPoll());

        voteEntity.addVotesToOption1(voteDto.getOption1Count());
        voteEntity.addVotesToOption2(voteDto.getOption2Count());

        VoteEntity updatedVote = voteRepository.save(voteEntity);

        VoteDto updatedVoteDto = modelMapper.map(updatedVote, VoteDto.class);

        LOGGER.debug("Done updating votes on current poll - {}", updatedVoteDto.getPollId());

        //return modelMapper.map(updatedVote, VoteDto.class)
        return updatedVoteDto;
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


        VoteEntity vote = findVoteForDeviceAndPoll(device, pollEntity);

        deviceRepository.save(device);

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);

        IoTDeviceDto updatedDevice = modelMapper.map(device, IoTDeviceDto.class);
        updatedDevice.setVotesForCurrentPoll(voteDto);

        LOGGER.debug("Done pairing poll with IoT-device");
        return updatedDevice;
    }

    @Override
    public List<IoTDeviceDto> getAllUnpairedDevices() {

        List<IoTDevice> devices = deviceRepository.findAllByCurrentPollIsNull();
        return devices.stream().map(device -> modelMapper.map(device, IoTDeviceDto.class)).collect(Collectors.toList());

    }

    private VoteEntity findVoteForDeviceAndPoll(IoTDevice device, PollEntity pollEntity) {
        VoteEntity vote = voteRepository.findByVoterAndPollEntity(device, pollEntity);

        if (vote == null) {
            vote = new VoteEntity();
            vote.setPollEntity(pollEntity);
            vote.setVoter(device);
            voteRepository.save(vote);
        }

        return vote;
    }

}
