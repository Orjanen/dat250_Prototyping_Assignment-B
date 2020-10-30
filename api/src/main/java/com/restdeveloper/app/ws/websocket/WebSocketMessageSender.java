package com.restdeveloper.app.ws.websocket;

import com.restdeveloper.app.ws.shared.WebSocketMessageConstants;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMessageSender {

    @Autowired
    SimpMessagingTemplate template;

    public void sendVoteMessageAfterVoteReceived(String pollId, VoteDto returnVote) {
        template.convertAndSend("/topic/poll/"
                        + pollId
                        + "/vote",
                WebSocketMessageConstants.POLL_UPDATE
                        + WebSocketMessageConstants.SEPARATOR
                        + pollId
                        + WebSocketMessageConstants.SEPARATOR
                        + returnVote.getOption1Count()
                        + WebSocketMessageConstants.SEPARATOR
                        + returnVote.getOption2Count()
        );
    }
}
