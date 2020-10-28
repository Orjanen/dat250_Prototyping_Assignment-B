package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.service.UserService;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.ui.model.request.PollsRequestModel;
import com.restdeveloper.app.ws.ui.model.response.OperationStatusModel;
import com.restdeveloper.app.ws.ui.model.response.PollRest;
import com.restdeveloper.app.ws.ui.model.response.RequestOperationStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(path = "/user/{userId}")
    public PollRest addNewPollByUser(@PathVariable String userId, @RequestBody PollsRequestModel newPoll){
        PollDto pollDto = modelMapper.map(newPoll, PollDto.class);
        PollDto savedPoll = pollService.createPoll(pollDto, userId);
        PollRest returnPoll = modelMapper.map(savedPoll, PollRest.class);
        return returnPoll;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "{id}")
    public OperationStatusModel deletePoll(@PathVariable String id){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        pollService.deletePoll(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

}
