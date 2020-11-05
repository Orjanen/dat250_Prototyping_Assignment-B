package com.restdeveloper.app.ws.service;

import com.restdeveloper.app.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUserByUserId(String id);
    UserDto getUser(String email);
    void deleteUser(String userId);
    UserDto updateUser(String id,UserDto user);
    void banUser(String userId);

    List<UserDto> getAllUsers();
}
