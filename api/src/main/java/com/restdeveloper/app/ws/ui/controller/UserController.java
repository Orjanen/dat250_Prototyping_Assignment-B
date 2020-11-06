package com.restdeveloper.app.ws.ui.controller;

import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.service.PollService;
import com.restdeveloper.app.ws.service.UserService;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.Roles;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.shared.dto.UserDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.UserDetailsRequestModel;
import com.restdeveloper.app.ws.ui.model.response.*;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            //Cannot return JSON, response message contains String
            String errorMessage =
                    "Request errors: " + bindingResult.getErrorCount() + " : " + bindingResult.getAllErrors().stream().map(be -> be.getDefaultMessage()).collect(Collectors.toList()).toString();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                              errorMessage
            );
        }

        LOGGER.debug("User-Controller initialized to create user");

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_USER.name())));
        UserDto createdUser = userService.createUser(userDto);

        return ResponseEntity.accepted().body(modelMapper.map(createdUser, UserRest.class));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorizationheader.description}",
                    paramType = "header", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserRest> getUser(@PathVariable String id) {
        LOGGER.debug("User-Controller initialized to get user bu ID");

        UserDto userDto;
        try {
            userDto = userService.getUserByUserId(id);

        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.accepted().body(modelMapper.map(userDto, UserRest.class));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorizationheader.description}",
                    paramType = "header", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @PutMapping(path = "{id}")
    public ResponseEntity<UserRest> updateUser(@RequestBody UserDetailsRequestModel userDetails,
                                               @PathVariable String id) {
        LOGGER.debug("User-Controller initialized to update user");

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto updatedUser;
        try {
            updatedUser = userService.updateUser(id, userDto);

        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.accepted().body(modelMapper.map(updatedUser, UserRest.class));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorizationheader.description}",
                    paramType = "header", dataTypeClass = String.class)
    })
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorizationheader.description}",
                    paramType = "header", dataTypeClass = String.class)
    })
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorizationheader.description}",
                    paramType = "header", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @GetMapping(path = "/{id}/polls")
    public ResponseEntity<List<PollRest>> findPollsCreatedByUser(@PathVariable("id") String id) {
        LOGGER.debug("User-Controller initialized to get all polls created by user");

        List<PollDto> pollDtos;
        try {
            pollDtos = pollService.getAllPollsByCreator(id);

        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        return ResponseEntity.accepted().body(pollDtos.stream().map(pollDto -> modelMapper.map(pollDto,
                                                                                               PollRest.class)).collect(Collectors.toList()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorizationheader.description}",
                    paramType = "header", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @GetMapping(path = "/{id}/votes")
    public ResponseEntity<List<VoteRest>> findAllVotesByUser(@PathVariable("id") String userId) {
        LOGGER.debug("User-Controller initialized to get all votes by user");

        List<VoteDto> voteDtos;
        try {
            voteDtos = voteService.getAllVotesByUser(userId);

        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        return ResponseEntity.accepted().body(voteDtos.stream().map(voteDto -> modelMapper.map(voteDto,
                                                                                               VoteRest.class)).collect(Collectors.toList()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorizationheader.description}",
                    paramType = "header", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<UserRest>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.accepted().body(userDtos.stream().map(userDto -> modelMapper.map(userDto, UserRest.class)).collect(Collectors.toList()));
    }

}
