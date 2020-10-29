package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.service.UserService;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.ui.model.request.PollsRequestModel;
import com.restdeveloper.app.ws.ui.model.response.PollRest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public PollRest getPoll(@PathVariable String id) {
        LOGGER.debug("Poll-Controller initialized to get poll by ID");

        PollRest returnValue;
        PollDto pollDto = pollService.getPollByPollId(id);
        returnValue = modelMapper.map(pollDto, PollRest.class);

        return returnValue;
    }

    @PostMapping(path = "/user/{userId}")
    public PollRest addNewPollByUser(@PathVariable String userId, @RequestBody PollsRequestModel newPoll) {
        LOGGER.debug("Poll-Controller initialized to add new poll by user");

        PollDto pollDto = modelMapper.map(newPoll, PollDto.class);
        PollDto savedPoll = pollService.createPoll(pollDto, userId);
        return modelMapper.map(savedPoll, PollRest.class);
    }
}
