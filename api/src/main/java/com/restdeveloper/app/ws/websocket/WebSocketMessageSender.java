package com.restdeveloper.app.ws.websocket;

import com.restdeveloper.app.ws.io.entity.PollEntity;
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
        template.convertAndSend("/topic/poll/"
                        + pollId
                        ,
                WebSocketMessageConstants.POLL_UPDATE
                        + WebSocketMessageConstants.SEPARATOR
                        + pollId
                        + WebSocketMessageConstants.SEPARATOR
                        + returnVote.getOption1Count()
                        + WebSocketMessageConstants.SEPARATOR
                        + returnVote.getOption2Count()
        );
    }

    public void sendFinishedPollMessage(PollEntity poll) {
        template.convertAndSend(
                "/topic/poll/"
                + poll.getPollId(),
                WebSocketMessageConstants.POLL_ENDED
                + WebSocketMessageConstants.SEPARATOR
                + poll.getPollId()
        );
    }

    public String generatePollStatusString(PollDto pollDto){
        return pollDto.getPollId()
                + WebSocketMessageConstants.SEPARATOR
                + pollDto.getPollName()
                + WebSocketMessageConstants.SEPARATOR
                + pollDto.getOptionOne()
                + WebSocketMessageConstants.SEPARATOR
                + pollDto.getOptionTwo()
                + WebSocketMessageConstants.SEPARATOR
                + pollDto.getOptionOneVotes()
                + WebSocketMessageConstants.SEPARATOR
                + pollDto.getOptionTwoVotes()
                + WebSocketMessageConstants.SEPARATOR +
                pollDto.getEndTime();
    }
}
