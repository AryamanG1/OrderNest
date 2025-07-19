package com.sample.Work.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sample.Work.model.UserEntity;
import com.sample.Work.payload.UserDeleteRequestDTO;
import com.sample.Work.payload.UserPasswordUpdateRequestDTO;
import com.sample.Work.payload.UserResponseDTO;
import com.sample.Work.payload.UserUpdateRequestDTO;
import com.sample.Work.repository.UserRepository;
import com.sample.Work.utils.ExceptionUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO updateUser(UserUpdateRequestDTO request) {
        logger.trace("Entered updateUser service");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Authenticated user: {}", auth.getName());

        UserEntity user = userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> {
                    logger.error("User not found for username: {}", auth.getName());
                    return ExceptionUtils.userNotFoundError(auth.getName());
                });

        if (request.getAge() > 0) {
            user.setAge(request.getAge());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }

        userRepository.save(user);
        logger.debug("Updated user: {}", user);
        logger.trace("Exited updateUser service");

        return new UserResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> viewUserAdmin() {
        logger.trace("Entered viewUserAdmin service");

        List<UserEntity> users = userRepository.findAll();
        List<UserResponseDTO> usersSafe = new ArrayList<>();

        for (UserEntity u : users) {
            usersSafe.add(new UserResponseDTO(u));
        }

        logger.debug("Retrieved {} user(s)", usersSafe.size());
        logger.trace("Exited viewUserAdmin service");

        return usersSafe;
    }

    @Override
    public UserResponseDTO deleteUserAdmin(UserDeleteRequestDTO request) {
        logger.trace("Entered deleteUserAdmin service");

        UserEntity user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> {
                    logger.error("User not found for deletion: {}", request.getUserName());
                    return ExceptionUtils.userNotFoundError(request.getUserName());
                });

        userRepository.delete(user);
        logger.debug("Deleted user: {}", user);
        logger.trace("Exited deleteUserAdmin service");

        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updatePassword(UserPasswordUpdateRequestDTO request) {
        logger.trace("Entered updatePassword service");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Authenticated user for password update: {}", auth.getName());

        UserEntity user = userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> {
                    logger.error("User not found for password update: {}", auth.getName());
                    return ExceptionUtils.userNotFoundError(auth.getName());
                });

        user.setPassword(request.getPassword());
        userRepository.save(user);

        logger.debug("Updated password for user: {}", user);
        logger.trace("Exited updatePassword service");

        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO deleteUserSelf() {
        logger.trace("Entered deleteUserSelf service");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Authenticated user for self-deletion: {}", auth.getName());

        UserEntity user = userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> {
                    logger.error("User not found for self-deletion: {}", auth.getName());
                    return new RuntimeException("User not found with username: " + auth.getName());
                });

        userRepository.delete(user);
        logger.debug("Deleted user (self): {}", user);
        logger.trace("Exited deleteUserSelf service");

        return new UserResponseDTO(user);
    }

    @Override
    public UserEntity viewUserDetails() {
        logger.trace("Entered viewUserDetails service");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Authenticated user for detail view: {}", auth.getName());

        UserEntity user = userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> {
                    logger.error("User details not found for: {}", auth.getName());
                    return new RuntimeException("Could not locate user details");
                });

        logger.trace("Exited viewUserDetails service");
        return user;
    }
}
