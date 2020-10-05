package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import com.restdeveloper.app.ws.ui.model.response.VoteRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vote")
public class VoteController {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    PollService pollService;

     @Autowired
    VoteService voteService;

    //TODO: Heller hente User fra authorization?
    @PostMapping(path = "/poll/{pollId}/user/{userId}")
    public VoteRest addVoteByRegisteredUserToPoll(@RequestBody VotingDetailsModel vote, @PathVariable("pollId") String pollId, @PathVariable("userId") String userId){
        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto savedVote = voteService.addVoteByRegisteredUserToPoll(voteDto, pollId, userId);
        VoteRest returnValue = modelMapper.map(savedVote, VoteRest.class);
        return returnValue;
    }

    @PutMapping(path = "/poll/{pollId}/user/{userId}")
    public VoteRest updateVoteByRegisteredUser(@RequestBody VotingDetailsModel vote, @PathVariable("pollId") String pollId, @PathVariable("userId") String userId){
        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto updatedVote = voteService.updateVote(voteDto, pollId, userId);
        VoteRest returnValue = modelMapper.map(updatedVote, VoteRest.class);
        return returnValue;
    }

    @PostMapping(path = "/poll/{pollId}")
    public VoteRest addVoteByUnregisteredUserToPoll(@RequestBody VotingDetailsModel vote, @PathVariable("pollId") String pollId){
        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto savedVote = voteService.addVoteByUnregisteredUserToPoll(voteDto, pollId);
        VoteRest returnValue = modelMapper.map(savedVote, VoteRest.class);
        return returnValue;
    }

}
