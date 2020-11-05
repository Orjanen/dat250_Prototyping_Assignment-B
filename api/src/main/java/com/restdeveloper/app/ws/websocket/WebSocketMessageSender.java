package com.restdeveloper.app.ws.websocket;

import com.restdeveloper.app.ws.shared.WebSocketMessageConstants;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMessageSender {

    @Autowired
    SimpMessagingTemplate template;


    public void sendVoteMessageAfterVoteReceived(String pollId, VoteDto returnVote) {

        String jsonString = WebSocketMessageConverter.convertVoteToJson(pollId, returnVote,
                                                                        WebSocketMessageConstants.POLL_UPDATE);
        template.convertAndSend("/topic/poll/" + pollId,
                                jsonString);
    }

    public void sendFinishedPollMessage(PollDto poll) {
        String jsonString = generatePollStatusString(poll, WebSocketMessageConstants.POLL_ENDED);

        template.convertAndSend("/topic/poll/" + poll.getPollId(),
                                jsonString);

    }

    public String generatePollStatusString(PollDto pollDto, String messageContext) {

        return WebSocketMessageConverter.convertPollToJson(pollDto, messageContext);
    }

    public void notifyDeviceAboutPairedPoll(String deviceId, PollDto pollDto) {
        String jsonString = generatePollStatusString(pollDto, WebSocketMessageConstants.PAIRED_WITH_NEW_CHANNEL);
        template.convertAndSend("/app/device/" + deviceId + "/", jsonString);
    }
}
