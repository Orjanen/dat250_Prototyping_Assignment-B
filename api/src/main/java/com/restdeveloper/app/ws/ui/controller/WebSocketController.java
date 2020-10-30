package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.IoTDeviceService;
import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.shared.WebSocketMessageConstants;
import com.restdeveloper.app.ws.shared.dto.IoTDeviceDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import com.restdeveloper.app.ws.websocket.WebSocketMessageSender;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    @MessageMapping("device/{deviceId}/vote/ws")
    public void receiveWebSocketVoteFromIotDevice(@DestinationVariable("deviceId") String deviceId, @Payload String message){


        String[] splitMessage = message.split(String.valueOf(WebSocketMessageConstants.SEPARATOR));

        //VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto voteDto = new VoteDto();
        voteDto.setOption1Count(Integer.parseInt(splitMessage[0]));
        voteDto.setOption2Count(Integer.parseInt(splitMessage[1]));

        VoteDto updatedVote = ioTDeviceService.updateVoteForCurrentPoll(deviceId, voteDto);

        webSocketMessageSender.sendVoteMessageAfterVoteReceived(updatedVote.getPollId(), voteDto);

    }


    //TODO: Handle websocket votes from React users



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
