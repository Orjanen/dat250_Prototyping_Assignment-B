package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.IoTDeviceService;
import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.shared.WebSocketMessageConstants;
import com.restdeveloper.app.ws.shared.dto.IoTDeviceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.restdeveloper.app.ws.shared.dto.PollDto;
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

    // TODO Autowire-problem: Missing bean
    @Autowired
    SimpMessagingTemplate template;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);


    @SubscribeMapping("poll/{pollId}/sub")
    public String confirmSubscriptionToPoll(@DestinationVariable String pollId){
        PollDto poll = pollService.getPollByPollId(pollId);

        //TODO: Implement handling of non-existing poll
        if(poll == null){
            return "Poll " + pollId + " does not exists";
        }
        LOGGER.debug("WebSocket-Controller initialized to confirm subscription to poll");

        return "Paired with poll: " + pollId;
    }

    /*
    When an IoT device connects - return the poll it's paired with
     */
    @SubscribeMapping("device/{deviceId}")
    public String handleNewDeviceConnection(@DestinationVariable String deviceId) {
        LOGGER.debug("WebSocket-Controller initialized to handle new device-connection");

        IoTDeviceDto deviceDto = ioTDeviceService.getIoTDeviceByPublicDeviceId(deviceId);
        if (deviceDto.getCurrentPoll() == null) {
            return WebSocketMessageConstants.NOT_PAIRED;
        } else{
            return WebSocketMessageConstants.PAIRED_WITH_NEW_CHANNEL + WebSocketMessageConstants.SEPARATOR + pollService.getCurrentPollStatusForWebSocket(deviceDto.getCurrentPoll().getPollId());
        }

    }

    /*
    @MessageMapping("poll/{pollId}/sub")
    public String notifySubscribersAboutUpdatedPoll(String pollUpdate)
        LOGGER.debug("WebSocket-Controller initialized to notify subscriber about updated poll")

        //return WebSocketMessageConstants.POLL_UPDATE + "Poll: " + pollDto.getPollId() + " - 1: " + pollDto
        .getOptionOneVotes() + " - 2: " + pollDto.getOptionTwoVotes()
        return pollUpdate
     */

}
