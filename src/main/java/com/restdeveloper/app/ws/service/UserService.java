package com.restdeveloper.app.ws.service;

import com.restdeveloper.app.ws.shared.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto getUserByUserId(String id);
    void deleteUser(String userId);
    UserDto updateUser(String id,UserDto user);
}
