package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.IoTDeviceService;
import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.shared.WebSocketMessageConstants;
import com.restdeveloper.app.ws.shared.dto.IoTDeviceDto;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;

@Controller
@Transactional
public class WebSocketController {


    @Autowired
    PollService pollService;

    @Autowired
    IoTDeviceService ioTDeviceService;

    @Autowired
    SimpMessagingTemplate template;


    @SubscribeMapping("poll/{pollId}/sub")
    public String confirmSubscriptionToPoll(@DestinationVariable String pollId){
        String pollStatus = pollService.getCurrentPollStatus(pollId);
        //template.convertAndSendToUser(pollStatus);
        return pollStatus;
    }

    //TODO: Message constants on server side
    @SubscribeMapping("device/{deviceId}")
    public String handleNewDeviceConnection(@DestinationVariable String deviceId){
        IoTDeviceDto deviceDto = ioTDeviceService.getIoTDeviceByPublicDeviceId(deviceId);
        if(deviceDto.getCurrentPoll() == null){
            return WebSocketMessageConstants.NOT_PAIRED;
        } else{
            return WebSocketMessageConstants.PAIRED_WITH_NEW_CHANNEL + deviceDto.getCurrentPoll().getPollId();
        }

    }

    /*
    @MessageMapping("poll/{pollId}/sub")
    public String notifySubscribersAboutUpdatedPoll(String pollUpdate){
        //return WebSocketMessageConstants.POLL_UPDATE + "Poll: " + pollDto.getPollId() + " - 1: " + pollDto.getOptionOneVotes() + " - 2: " + pollDto.getOptionTwoVotes();
        return pollUpdate;
    }


     */

}
