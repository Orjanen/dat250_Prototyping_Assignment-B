package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.IoTDeviceService;
import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.shared.WebSocketMessageConstants;
import com.restdeveloper.app.ws.shared.dto.IoTDeviceDto;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.websocket.WebSocketMessageSender;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
    WebSocketMessageSender webSocketMessageSender;

    ModelMapper modelMapper = new ModelMapper();


    @Autowired
    SimpMessagingTemplate template;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);


    @MessageMapping("device/{deviceId}/vote/ws")
    public void receiveWebSocketVoteFromIotDevice(@DestinationVariable("deviceId") String deviceId,
                                                  @Payload String message) {

        LOGGER.info("Sending vote to device: " + deviceId);

        String[] splitMessage = message.split(String.valueOf(WebSocketMessageConstants.SEPARATOR));

        //VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto voteDto = new VoteDto();
        voteDto.setOption1Count(Integer.parseInt(splitMessage[0]));
        voteDto.setOption2Count(Integer.parseInt(splitMessage[1]));

        VoteDto updatedVote = ioTDeviceService.updateVoteForCurrentPoll(deviceId, voteDto);

        webSocketMessageSender.sendVoteMessageAfterVoteReceived(updatedVote.getPollId(), voteDto);

    }


    @MessageMapping("device/{deviceId}")
    public String handleDeviceMessage(@DestinationVariable String deviceId, @Payload String payload){
        LOGGER.info("Received message for queue: /device/" + deviceId + " : " + payload);
        //if (payload.startsWith())
        return payload;
    }







    /*
    When an IoT device connects - return the poll it's paired with
     */
    @SubscribeMapping("device/{deviceId}")
    public String handleNewDeviceConnection(@DestinationVariable String deviceId) {
        LOGGER.info("WebSocket-Controller initialized to handle new device-connection");

        IoTDeviceDto deviceDto = ioTDeviceService.getIoTDeviceByPublicDeviceId(deviceId);
        if (deviceDto.getCurrentPoll() == null) {
            return WebSocketMessageConstants.NOT_PAIRED;
        } else {
            PollDto pollDto = pollService.getCurrentPollStatusForWebSocket(deviceDto.getCurrentPoll().getPollId());
            return webSocketMessageSender.generatePollStatusString(pollDto,
                                                                   WebSocketMessageConstants.PAIRED_WITH_NEW_CHANNEL);

            //return WebSocketMessageConstants.PAIRED_WITH_NEW_CHANNEL + WebSocketMessageConstants.SEPARATOR +
            // webSocketMessageSender.generatePollStatusString(pollDto);
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
