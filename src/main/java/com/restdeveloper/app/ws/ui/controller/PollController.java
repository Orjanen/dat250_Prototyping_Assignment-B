package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.service.UserService;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.PollsRequestModel;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import com.restdeveloper.app.ws.ui.model.response.PollRest;
import com.restdeveloper.app.ws.ui.model.response.VoteRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("poll")
public class PollController {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserService userService;

    @Autowired
    PollService pollService;

    @Autowired
    VoteService voteService;

    @PutMapping
    public String updatePoll(){
        return "put poll was called";
    }

    @GetMapping(path = "/{id}")
    public PollRest getPoll(@PathVariable String id){
        PollRest returnValue;
        PollDto pollDto = pollService.getPollByPollId(id);
        returnValue = modelMapper.map(pollDto, PollRest.class);

        return returnValue;


    }

    @PostMapping(path = "/user/{id}/createpoll")
    public PollRest createPoll(@RequestBody PollsRequestModel pollDetails, @PathVariable String id){

        PollDto pollDto = modelMapper.map(pollDetails, PollDto.class);
        PollDto createPoll = pollService.createPoll(pollDto, id);
        PollRest returnValue = modelMapper.map(createPoll, PollRest.class);

        return returnValue;
    }

    //TODO: Heller hente User fra authorization?
    @PostMapping(path = "/{pollId}/user/{userId}/vote")
    public VoteRest addVoteByRegisteredUserToPoll(@RequestBody VotingDetailsModel vote, @PathVariable("pollId") String pollId, @PathVariable("userId") String userId){
        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto savedVote = voteService.addVoteByRegisteredUserToPoll(voteDto, pollId, userId);
        VoteRest returnValue = modelMapper.map(savedVote, VoteRest.class);
        return returnValue;

    }

    @PostMapping(path = "/{pollId}/vote")
    public VoteRest addVoteByUnregisteredUserToPoll(@RequestBody VotingDetailsModel vote, @PathVariable("pollId") String pollId){
        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto savedVote = voteService.addVoteByUnregisteredUserToPoll(voteDto, pollId);
        VoteRest returnValue = modelMapper.map(savedVote, VoteRest.class);
        return returnValue;
    }
}
