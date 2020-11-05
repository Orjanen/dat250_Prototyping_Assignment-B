package com.restdeveloper.app.ws.service.implementation;

import com.restdeveloper.app.ws.io.entity.RoleEntity;
import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.io.repository.RoleRepository;
import com.restdeveloper.app.ws.io.repository.UserRepository;
import com.restdeveloper.app.ws.service.UserService;
import com.restdeveloper.app.ws.shared.Utils;
import com.restdeveloper.app.ws.shared.dto.UserDto;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    Utils utils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String COULD_NOT_FIND_USER_WITH_USER_ID = "Could not find user with user-ID: ";
    private static final String COULD_NOT_FIND_USER_WITH_EMAIL = "Could not find user with given email";

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDto createUser(UserDto user) {
        LOGGER.info("Creating new user");

        if (userRepository.findByEmail(user.getEmail()) != null) {
            LOGGER.error("Email already in use");
            throw new IllegalArgumentException("Email already in use");
        }

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserId(utils.generateUserId(30));

        //set roles
        Collection<RoleEntity> roleEntities = new HashSet<>();
        for (String role : user.getRoles()) {
            RoleEntity roleEntity = roleRepository.findByName(role);
            if (roleEntity != null) {
                roleEntities.add(roleEntity);
            }
        }
        userEntity.setRoles(roleEntities);

        UserEntity storedUserDetails = userRepository.save(userEntity);

        LOGGER.debug("Done creating user");
        return modelMapper.map(storedUserDetails, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String id) {
        LOGGER.info("Getting user with user-ID: {}", id);

        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null) {
            String errorMessage = COULD_NOT_FIND_USER_WITH_USER_ID + id;
            LOGGER.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        LOGGER.debug("Done getting user");
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUser(String email) {
        LOGGER.info("Getting user by email");

        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            LOGGER.error(COULD_NOT_FIND_USER_WITH_EMAIL);
            throw new UsernameNotFoundException(COULD_NOT_FIND_USER_WITH_EMAIL);
        }

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);

        LOGGER.debug("Done getting user");
        return returnValue;

    }

    @Override
    public void deleteUser(String userId) {
        LOGGER.info("Deleting user with user-ID: {}", userId);

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            String errorMessage = COULD_NOT_FIND_USER_WITH_USER_ID + userId;
            LOGGER.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        userRepository.delete(userEntity);
        LOGGER.debug("Done deleting user");
    }


    @Override
    public UserDto updateUser(String id, UserDto user) {
        LOGGER.info("Updating user with user-ID: {}", id);

        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null) {
            String errorMessage = COULD_NOT_FIND_USER_WITH_USER_ID + id;
            LOGGER.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        //TODO null check and other values to update?
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUser = userRepository.save(userEntity);

        LOGGER.debug("Done updating user");
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void banUser(String userId) {
        LOGGER.info("Banning user with uder-ID: {}", userId);

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            String errorMessage = COULD_NOT_FIND_USER_WITH_USER_ID + userId;
            LOGGER.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        userEntity.setBanStatus(true);
        userRepository.save(userEntity);

        LOGGER.debug("Done banning user");
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<UserDto> users = new ArrayList<>();
        userRepository.findAll().forEach(userEntity -> users.add(modelMapper.map(userEntity, UserDto.class)));
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        LOGGER.info("Loading user by username");
        UserEntity userEntity = userRepository.findByEmail(email);

        LOGGER.debug("Done loading user");
        return new UserPrincipals(userEntity);
        //if (userEntity == null) throw new UsernameNotFoundException(email)
        //return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>())
    }
}
