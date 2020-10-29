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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserService userService;

    @Autowired
    PollService pollService;

    @Autowired
    VoteService voteService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        LOGGER.debug("User-Controller initialized to create user");

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_USER.name())));
        UserDto createdUser = userService.createUser(userDto);
        return modelMapper.map(createdUser, UserRest.class);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {
        LOGGER.debug("User-Controller initialized to get user bu ID");

        UserDto userDto = userService.getUserByUserId(id);
        return modelMapper.map(userDto, UserRest.class);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @PutMapping(path = "{id}")
    public UserRest updateUser(@RequestBody UserDetailsRequestModel userDetails, @PathVariable String id) {
        LOGGER.debug("User-Controller initialized to update user");

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto updatedUser = userService.updateUser(id, userDto);
        return modelMapper.map(updatedUser, UserRest.class);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @DeleteMapping(path = "{id}")
    public OperationStatusModel deleteUser(@PathVariable String id) {
        LOGGER.debug("User-Controller initialized to delete user");

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = "{id}/ban")
    public OperationStatusModel banUser(@PathVariable String id) {
        LOGGER.debug("User-Controller initialized to ban user");

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.BAN.name());
        userService.banUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @GetMapping(path = "/{id}/polls")
    public List<PollRest> findPollsCreatedByUser(@PathVariable("id") String id) {
        LOGGER.debug("User-Controller initialized to get all polls created by user");

        List<PollDto> pollDtos = pollService.getAllPollsByCreator(id);
        return pollDtos.stream().map(pollDto -> modelMapper.map(pollDto, PollRest.class)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @GetMapping(path = "/{id}/votes")
    public List<VoteRest> findAllVotesByUser(@PathVariable("id") String userId) {
        LOGGER.debug("User-Controller initialized to get all votes by user");

        List<VoteDto> voteDtos = voteService.getAllVotesByUser(userId);
        return voteDtos.stream().map(voteDto -> modelMapper.map(voteDto, VoteRest.class)).collect(Collectors.toList());
    }

}
