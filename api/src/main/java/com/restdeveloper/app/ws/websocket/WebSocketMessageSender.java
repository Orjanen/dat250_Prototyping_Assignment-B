package com.restdeveloper.app.ws.websocket;

import com.google.gson.Gson;
import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.shared.WebSocketMessageConstants;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMessageSender {

    @Autowired
    SimpMessagingTemplate template;



    public void sendVoteMessageAfterVoteReceived(String pollId, VoteDto returnVote) {

        String jsonString = WebSocketMessageConverter.convertVoteToJson(pollId, returnVote, WebSocketMessageConstants.POLL_UPDATE);
        template.convertAndSend("/topic/poll/" + pollId,
                jsonString);
    }

    public void sendFinishedPollMessage(PollDto poll) {
        String jsonString = generatePollStatusString(poll, WebSocketMessageConstants.POLL_ENDED);

        template.convertAndSend("/topic/poll/" + poll.getPollId(),
                jsonString);

    }

    public String generatePollStatusString(PollDto pollDto, String messageContext){

        String jsonString = WebSocketMessageConverter.convertPollToJson(pollDto, messageContext);
        return jsonString;

    }
}
