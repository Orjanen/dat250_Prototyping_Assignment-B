package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.shared.exceptions.UnregisteredVoteForPrivatePollException;
import com.restdeveloper.app.ws.shared.exceptions.VoteCastForFinishedPollException;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import com.restdeveloper.app.ws.ui.model.response.VoteRest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("vote")
public class VoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteController.class);

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    PollService pollService;

     @Autowired
    VoteService voteService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization",value="${userController.authorizationheader.description}", paramType = "header", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(path = "/poll/{pollId}/user/{userId}")
    public ResponseEntity<VoteRest> addVoteByRegisteredUserToPoll(@RequestBody VotingDetailsModel vote, @PathVariable("pollId") String pollId, @PathVariable("userId") String userId){
        LOGGER.debug("Vote-Controller initialized to add votes by registered user");

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
        VoteDto savedVote;




        try{
            savedVote = voteService.addVoteByRegisteredUserToPoll(voteDto, pollId, userId);

        } catch(ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(VoteCastForFinishedPollException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Poll is finished, not accepting new votes!");
        }

        return ResponseEntity.accepted().body(modelMapper.map(savedVote, VoteRest.class));

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization",value="${userController.authorizationheader.description}", paramType = "header", dataTypeClass = String.class)
    })
    @PreAuthorize("#userId == principal.userId")
    @PutMapping(path = "/poll/{pollId}/user/{userId}")
    public ResponseEntity<VoteRest> updateVoteByRegisteredUser(@RequestBody VotingDetailsModel vote, @PathVariable("pollId") String pollId, @PathVariable("userId") String userId){
        LOGGER.debug("Vote-Controller initialized to update votes by registered user");

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);

        VoteDto updatedVote;
        try{
            updatedVote = voteService.updateVote(voteDto, pollId, userId);
        } catch(ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(VoteCastForFinishedPollException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }

        return ResponseEntity.accepted().body(modelMapper.map(updatedVote, VoteRest.class));
    }

    @PostMapping(path = "/poll/{pollId}")
    public ResponseEntity<VoteRest> addVoteByUnregisteredUserToPoll(@RequestBody VotingDetailsModel vote, @PathVariable("pollId") String pollId){
        LOGGER.debug("Vote-Controller initialized to add votes by unregistered user");

        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);

        VoteDto savedVote;
        try{
            savedVote = voteService.addVoteByUnregisteredUserToPoll(voteDto, pollId);

        } catch(ResourceNotFoundException e){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(VoteCastForFinishedPollException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Poll is finished, not accepting new votes!");
        } catch(UnregisteredVoteForPrivatePollException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Poll requires registered voters!");
        }

        return ResponseEntity.accepted().body(modelMapper.map(savedVote, VoteRest.class));
    }

}
