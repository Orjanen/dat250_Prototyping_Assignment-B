package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.IoTDeviceService;
import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.shared.WebSocketMessageConstants;
import com.restdeveloper.app.ws.shared.dto.IoTDeviceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

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
        //String pollStatus =  WebSocketMessageConstants.POLL_UPDATE + WebSocketMessageConstants.SEPARATOR + pollService.getCurrentPollStatusForWebSocket(pollId);
        //template.convertAndSendToUser(pollStatus);
        return "Paired with poll: " + pollId;
    }

    /*
    When an IoT device connects - return the poll it's paired with
     */
    @SubscribeMapping("device/{deviceId}")
    public String handleNewDeviceConnection(@DestinationVariable String deviceId){
        IoTDeviceDto deviceDto = ioTDeviceService.getIoTDeviceByPublicDeviceId(deviceId);
        if(deviceDto.getCurrentPoll() == null){
            return WebSocketMessageConstants.NOT_PAIRED;
        } else{
            return WebSocketMessageConstants.PAIRED_WITH_NEW_CHANNEL + WebSocketMessageConstants.SEPARATOR + pollService.getCurrentPollStatusForWebSocket(deviceDto.getCurrentPoll().getPollId());
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
