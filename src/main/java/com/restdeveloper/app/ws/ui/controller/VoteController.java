package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vote")
public class VoteController {

    @Autowired
    PollService pollService;

   @Autowired
   VoteService voteService;

    @PutMapping(path = "/poll/{pollId}")
    public String vote(@PathVariable String pollId, @RequestBody VotingDetailsModel votingDetails){

        PollDto pollDto = pollService.getPollByPollId(pollId);
        voteService.updateVotes(pollDto.getVoteDetails().getVoteId(),votingDetails);

        return "put Vote was called";
    }
}
