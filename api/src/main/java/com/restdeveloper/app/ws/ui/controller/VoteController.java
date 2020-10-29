package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import com.restdeveloper.app.ws.ui.model.response.VoteRest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vote")
public class VoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteController.class);

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    PollService pollService;

     @Autowired
    VoteService voteService;

    //TODO: Heller hente User fra authorization?
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(path = "/poll/{pollId}/user/{userId}")
    public VoteRest addVoteByRegisteredUserToPoll(@RequestBody VotingDetailsModel vote, @PathVariable("pollId") String pollId, @PathVariable("userId") String userId){
        LOGGER.debug("Vote-Controller initialized to add votes by registered user");

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto savedVote = voteService.addVoteByRegisteredUserToPoll(voteDto, pollId, userId);
        return modelMapper.map(savedVote, VoteRest.class);
    }

    @PreAuthorize("#userId == principal.userId")
    @PutMapping(path = "/poll/{pollId}/user/{userId}")
    public VoteRest updateVoteByRegisteredUser(@RequestBody VotingDetailsModel vote, @PathVariable("pollId") String pollId, @PathVariable("userId") String userId){
        LOGGER.debug("Vote-Controller initialized to update votes by registered user");

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto updatedVote = voteService.updateVote(voteDto, pollId, userId);
        return modelMapper.map(updatedVote, VoteRest.class);
    }

    @PostMapping(path = "/poll/{pollId}")
    public VoteRest addVoteByUnregisteredUserToPoll(@RequestBody VotingDetailsModel vote, @PathVariable("pollId") String pollId){
        LOGGER.debug("Vote-Controller initialized to add votes by unregistered user");

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto savedVote = voteService.addVoteByUnregisteredUserToPoll(voteDto, pollId);
        return modelMapper.map(savedVote, VoteRest.class);
    }

}
