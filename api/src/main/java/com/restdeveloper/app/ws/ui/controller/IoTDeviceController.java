package com.restdeveloper.app.ws.ui.controller;


import com.restdeveloper.app.ws.service.IoTDeviceService;
import com.restdeveloper.app.ws.shared.dto.IoTDeviceDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.IoTDeviceRequestModel;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import com.restdeveloper.app.ws.ui.model.response.IoTDeviceRest;
import com.restdeveloper.app.ws.ui.model.response.VoteRest;
import com.restdeveloper.app.ws.websocket.WebSocketMessageSender;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("device")
public class IoTDeviceController {

    static final String NOT_PAIRED = "Device is not currently paired with a poll";
    static final String NOT_REGISTERED = "Device is not registered. Please register the device.";


    @Autowired
    IoTDeviceService ioTDeviceService;

    @Autowired
    WebSocketMessageSender webSocketMessageSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(IoTDeviceController.class);

    ModelMapper modelMapper = new ModelMapper();


    @GetMapping(path = "/{deviceId}")
    public IoTDeviceRest getIoTDeviceByPublicDeviceId(@PathVariable("deviceId") String publicDeviceId) {
        LOGGER.debug("IoT-Controller initialized to get IoT-device by public device-ID");

        IoTDeviceDto deviceDto = ioTDeviceService.getIoTDeviceByPublicDeviceId(publicDeviceId);
        return modelMapper.map(deviceDto, IoTDeviceRest.class);
    }

    @GetMapping(path = "/{deviceId}/poll")
    public String getPairedPoll(@PathVariable("deviceId") String deviceId) {
        LOGGER.debug("IoT-Controller initialized to get paired poll");

        IoTDeviceDto device = ioTDeviceService.getIoTDeviceByPublicDeviceId(deviceId);
        if (device == null) {
            return NOT_REGISTERED;
        }

        String pollId = ioTDeviceService.getPairedPoll(deviceId);
        if (pollId == null) {
            return NOT_PAIRED;
        } else {
            return pollId;
        }

    }

    @PutMapping(path = "/{deviceId}/vote")
    public VoteRest updateVoteForCurrentPoll(@PathVariable String deviceId, @RequestBody VotingDetailsModel vote) {
        LOGGER.debug("IoT-Controller initialized to update vote for current poll");

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto updatedTotalVote = ioTDeviceService.updateVoteForCurrentPoll(deviceId, voteDto);

        webSocketMessageSender.sendVoteMessageAfterVoteReceived(voteDto.getPollId(), voteDto);

        return modelMapper.map(updatedTotalVote, VoteRest.class);
    }

    @PutMapping(path = "{deviceId}/poll/{pollId}")
    public IoTDeviceRest setNewCurrentPoll(@PathVariable("deviceId") String deviceId,
                                           @PathVariable("pollId") String pollId) {
        LOGGER.debug("IoT-Controller initialized to set new current poll");

        //TODO: Don't allow changing polls until the last one is done?
        IoTDeviceDto deviceDto = ioTDeviceService.setPairedPoll(deviceId, pollId);
        return modelMapper.map(deviceDto, IoTDeviceRest.class);

    }


    @PostMapping("/")
    public IoTDeviceRest registerNewDevice(@RequestBody IoTDeviceRequestModel newDevice) {
        LOGGER.debug("IoT-Controller initialized to register new device");

        IoTDeviceDto ioTDeviceDto = modelMapper.map(newDevice, IoTDeviceDto.class);

        IoTDeviceDto deviceDto = ioTDeviceService.addNewDevice(ioTDeviceDto);
        return modelMapper.map(deviceDto, IoTDeviceRest.class);
    }

}
