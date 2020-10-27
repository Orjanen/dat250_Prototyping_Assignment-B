package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.service.UserService;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.Roles;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.shared.dto.UserDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.UserDetailsRequestModel;
import com.restdeveloper.app.ws.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserService userService;

    @Autowired
    PollService pollService;

    @Autowired
    VoteService voteService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_USER.name())));
        UserDto createdUser = userService.createUser(userDto);
        UserRest returnValue = modelMapper.map(createdUser, UserRest.class);

        return returnValue;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id){
        ModelMapper modelMapper = new ModelMapper();
        UserRest returnValue;
        UserDto userDto = userService.getUserByUserId(id);
        returnValue = modelMapper.map(userDto, UserRest.class);

        return returnValue;
    }


    @PutMapping(path = "{id}")
    public UserRest updateUser(@RequestBody UserDetailsRequestModel userDetails, @PathVariable String id){
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto updatedUser = userService.updateUser(id,userDto);
        UserRest returnValue = modelMapper.map(updatedUser, UserRest.class);

        return returnValue;

    }

    //@Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @DeleteMapping(path = "{id}")
    public OperationStatusModel deleteUser(@PathVariable String id){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }


    @GetMapping(path = "/{id}/polls")
    public List<PollRest> findPollsCreatedByUser(@PathVariable("id") String id){
        List<PollDto> pollDtos = pollService.getAllPollsByCreator(id);
        List<PollRest> pollRests = pollDtos.stream().map(pollDto -> modelMapper.map(pollDto, PollRest.class)).collect(Collectors.toList());
        return pollRests;
    }


    @GetMapping(path = "/{id}/votes")
    public List<VoteRest> findAllVotesByUser(@PathVariable("id") String userId){
        List<VoteDto> voteDtos = voteService.getAllVotesByUser(userId);
        List<VoteRest> voteRests = voteDtos.stream().map(voteDto -> modelMapper.map(voteDto, VoteRest.class)).collect(Collectors.toList());

        return voteRests;
    }


}
