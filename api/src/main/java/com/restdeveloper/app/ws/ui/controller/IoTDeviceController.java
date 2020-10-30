package com.restdeveloper.app.ws.ui.controller;


import com.restdeveloper.app.ws.io.entity.IoTDevice;
import com.restdeveloper.app.ws.io.repository.IoTDeviceRepository;
import com.restdeveloper.app.ws.service.IoTDeviceService;
import com.restdeveloper.app.ws.shared.dto.IoTDeviceDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import com.restdeveloper.app.ws.ui.model.response.IoTDeviceRest;
import com.restdeveloper.app.ws.ui.model.response.VoteRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("device")
public class IoTDeviceController {

    final String NOT_PAIRED = "Device is not currently paired with a poll";
    final String NOT_REGISTERED = "Device is not registered. Please register the device.";

    @Autowired
    IoTDeviceService deviceService;

    ModelMapper modelMapper = new ModelMapper();



    @GetMapping(path = "/{deviceId}")
    public IoTDeviceRest getIoTDeviceByPublicDeviceId(@PathVariable("deviceId") String publicDeviceId) {
        IoTDeviceDto deviceDto = deviceService.getIoTDeviceByPublicDeviceId(publicDeviceId);
        return modelMapper.map(deviceDto, IoTDeviceRest.class);
    }

    @GetMapping(path = "/{deviceId}/poll")
    public String getPairedPoll(@PathVariable("deviceId") String deviceId){

        IoTDeviceDto device = deviceService.getIoTDeviceByPublicDeviceId(deviceId);
        if(device == null){
            return NOT_REGISTERED;
        }

        String pollId = deviceService.getPairedPoll(deviceId);
        if(pollId == null){
            return NOT_PAIRED;
        } else{
            return pollId;
        }

    }

    @PutMapping(path = "/{deviceId}/vote")
    public VoteRest updateVoteForCurrentPoll(@PathVariable String deviceId, @RequestBody VotingDetailsModel vote){
        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto updatedTotalVote = deviceService.updateVoteForCurrentPoll(deviceId, voteDto);
        return modelMapper.map(updatedTotalVote, VoteRest.class);
    }

    @PutMapping(path = "{deviceId}/poll/{pollId}")
    public IoTDeviceRest setNewCurrentPoll(@PathVariable("deviceId") String deviceId, @PathVariable("pollId") String pollId){
        //TODO: Don't allow changing polls until the last one is done?
        IoTDeviceDto deviceDto =  deviceService.setPairedPoll(deviceId, pollId);
        return modelMapper.map(deviceDto, IoTDeviceRest.class);

    }

    //TODO: Change to body
    @PostMapping(path = "{deviceId}")
    public IoTDeviceRest registerNewDevice(@PathVariable("deviceId") String deviceId){

        IoTDeviceDto deviceDto = deviceService.addNewDevice(deviceId);
        return modelMapper.map(deviceDto, IoTDeviceRest.class);
    }




}
