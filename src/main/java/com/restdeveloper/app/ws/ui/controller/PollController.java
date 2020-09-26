package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.service.UserService;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.ui.model.request.PollsRequestModel;
import com.restdeveloper.app.ws.ui.model.response.PollRest;
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

    @PutMapping
    public String updateUser(){
        return "post poll was called";
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
}
