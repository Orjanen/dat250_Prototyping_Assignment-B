package com.restdeveloper.app.ws.ui.controller;


import com.restdeveloper.app.ws.io.entity.IoTDevice;
import com.restdeveloper.app.ws.service.IoTDeviceService;
import com.restdeveloper.app.ws.shared.WebSocketMessageConstants;
import com.restdeveloper.app.ws.shared.dto.IoTDeviceDto;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.IoTDeviceRequestModel;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import com.restdeveloper.app.ws.ui.model.response.IoTDeviceRest;
import com.restdeveloper.app.ws.ui.model.response.PollRest;
import com.restdeveloper.app.ws.ui.model.response.VoteRest;
import com.restdeveloper.app.ws.websocket.WebSocketMessageSender;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("device")
public class IoTDeviceController {


    static final String NOT_REGISTERED = "Device is not registered. Please register the device.";


    @Autowired
    IoTDeviceService ioTDeviceService;

    @Autowired
    WebSocketMessageSender webSocketMessageSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(IoTDeviceController.class);

    ModelMapper modelMapper = new ModelMapper();


    @GetMapping(path = "/{deviceId}")
    public ResponseEntity<IoTDeviceRest> getIoTDeviceByPublicDeviceId(@PathVariable("deviceId") String publicDeviceId) {
        LOGGER.debug("IoT-Controller initialized to get IoT-device by public device-ID");

        IoTDeviceDto deviceDto;
        try{
            deviceDto = ioTDeviceService.getIoTDeviceByPublicDeviceId(publicDeviceId);

        } catch(ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.accepted().body(modelMapper.map(deviceDto, IoTDeviceRest.class));
    }

    @GetMapping(path = "/{deviceId}/poll")
    public ResponseEntity<PollRest> getPairedPoll(@PathVariable("deviceId") String deviceId) {
        LOGGER.debug("IoT-Controller initialized to get paired poll");

        IoTDeviceDto device;
        try{
            device = ioTDeviceService.getIoTDeviceByPublicDeviceId(deviceId);
        } catch(ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, WebSocketMessageConstants.NOT_REGISTERED + WebSocketMessageConstants.SEPARATOR + e.getMessage());
        }


        PollDto pollDto;
        try{
            pollDto = ioTDeviceService.getPairedPoll(deviceId);

        } catch(ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, WebSocketMessageConstants.NOT_PAIRED + WebSocketMessageConstants.SEPARATOR + e.getMessage());
        }



        return ResponseEntity.accepted().body(modelMapper.map(pollDto, PollRest.class));


    }

    @PutMapping(path = "/{deviceId}/vote")
    public ResponseEntity<VoteRest> updateVoteForCurrentPoll(@PathVariable String deviceId, @RequestBody VotingDetailsModel vote) {
        LOGGER.debug("IoT-Controller initialized to update vote for current poll");

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);

        VoteDto updatedTotalVote;

        try{
            updatedTotalVote = ioTDeviceService.updateVoteForCurrentPoll(deviceId, voteDto);

        } catch(IllegalStateException e){
            //Device has not been paired yet
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, WebSocketMessageConstants.NOT_PAIRED + WebSocketMessageConstants.SEPARATOR + e.getMessage());

        }

        webSocketMessageSender.sendVoteMessageAfterVoteReceived(voteDto.getPollId(), voteDto);

        return ResponseEntity.accepted().body(modelMapper.map(updatedTotalVote, VoteRest.class));
    }

    @PutMapping(path = "{deviceId}/poll/{pollId}")
    public ResponseEntity<IoTDeviceRest> setNewCurrentPoll(@PathVariable("deviceId") String deviceId,
                                           @PathVariable("pollId") String pollId) {
        LOGGER.debug("IoT-Controller initialized to set new current poll");

        //TODO: Don't allow changing polls until the last one is done?
        IoTDeviceDto deviceDto;
        try{
            deviceDto = ioTDeviceService.setPairedPoll(deviceId, pollId);

        } catch(ResourceNotFoundException e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        LOGGER.info("IoT-Device: {} has been paired with poll: {}", deviceId, pollId);

        webSocketMessageSender.notifyDeviceAboutPairedPoll(deviceId, deviceDto.getCurrentPoll());
        return ResponseEntity.accepted().body(modelMapper.map(deviceDto, IoTDeviceRest.class));

    }


    @PostMapping("/")
    public IoTDeviceRest registerNewDevice(@RequestBody IoTDeviceRequestModel newDevice) {
        LOGGER.debug("IoT-Controller initialized to register new device");

        IoTDeviceDto ioTDeviceDto = modelMapper.map(newDevice, IoTDeviceDto.class);

        IoTDeviceDto deviceDto = ioTDeviceService.addNewDevice(ioTDeviceDto);
        return modelMapper.map(deviceDto, IoTDeviceRest.class);
    }

    @GetMapping("/unpaired")
    public List<IoTDeviceRest> getAllUnpairedDevices(){
        LOGGER.debug("IoT-Controller initialized to get all devices not paired with a poll");

        List<IoTDeviceDto> unpairedDevices = ioTDeviceService.getAllUnpairedDevices();

        return unpairedDevices.stream().map(device -> modelMapper.map(device, IoTDeviceRest.class)).collect(Collectors.toList());
    }

}
