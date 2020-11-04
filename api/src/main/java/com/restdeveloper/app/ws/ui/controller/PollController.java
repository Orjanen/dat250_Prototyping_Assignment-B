package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.service.UserService;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.ui.model.request.PollsRequestModel;
import com.restdeveloper.app.ws.ui.model.response.OperationStatusModel;
import com.restdeveloper.app.ws.ui.model.response.PollRest;
import com.restdeveloper.app.ws.ui.model.response.RequestOperationStatus;
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
@RequestMapping("poll")
public class PollController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollController.class);

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserService userService;

    @Autowired
    PollService pollService;

    @Autowired
    VoteService voteService;

    @PutMapping
    public String updatePoll() {
        LOGGER.debug("Poll-Controller initialized to update poll");
        return "put poll was called";
    }



    @GetMapping(path = "/{id}")
    public ResponseEntity<PollRest> getPoll(@PathVariable String id) {
        LOGGER.debug("Poll-Controller initialized to get poll by ID");

        PollDto pollDto;
        try{
            pollDto = pollService.getPollByPollId(id);

        } catch (ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }


        return ResponseEntity.accepted().body(modelMapper.map(pollDto, PollRest.class));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization",value="${userController.authorizationheader.description}", paramType = "header")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(path = "/user/{userId}")
    public ResponseEntity<PollRest> addNewPollByUser(@PathVariable String userId, @RequestBody PollsRequestModel newPoll) {
        LOGGER.debug("Poll-Controller initialized to add new poll by user");

        PollDto pollDto = modelMapper.map(newPoll, PollDto.class);

        PollDto savedPoll;
        try{
            savedPoll = pollService.createPoll(pollDto, userId);

        } catch(ResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        return ResponseEntity.accepted().body(modelMapper.map(savedPoll, PollRest.class));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization",value="${userController.authorizationheader.description}", paramType = "header")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "{id}")
    public OperationStatusModel deletePoll(@PathVariable String id){
        LOGGER.debug("Poll-Controller initialized to delete poll by ID");
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        pollService.deletePoll(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

}
