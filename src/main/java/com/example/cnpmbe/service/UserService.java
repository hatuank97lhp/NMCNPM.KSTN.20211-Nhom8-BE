package com.example.cnpmbe.service;


import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.common.APIResponseBuilder;
import com.example.cnpmbe.common.enums.ExceptionMessages;
import com.example.cnpmbe.common.enums.ResultMessages;
import com.example.cnpmbe.model.entity.auth.User;
import com.example.cnpmbe.model.request.auth.UserRequest;
import com.example.cnpmbe.model.response.UserSimpleResponse;
import com.example.cnpmbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<APIResponse> createUser(HttpServletRequest req, UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findUserByUsernameAndIsDeletedIsFalse(userRequest.getUsername());
        if (userOptional.isPresent()) {
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.USER_USERNAME_EMAIL_EXIST));
        }

        User user = new User(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user = userRepository.save(user);

        UserSimpleResponse userSimpleResponse = new UserSimpleResponse();
        userSimpleResponse.setId(user.getId());
        userSimpleResponse.setName(user.getName());
        userSimpleResponse.setIsActive(user.getIsActive());
        userSimpleResponse.setUsername(user.getUsername());

        return ResponseEntity.ok(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, userSimpleResponse));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getAllUsers(HttpServletRequest req) {
        List<User> users = userRepository.findAllByIsDeletedIsFalse();
        List<UserSimpleResponse> userSimpleResponses= new ArrayList<>();
        for (User user : users) {
            UserSimpleResponse userSimpleResponse = new UserSimpleResponse();
            userSimpleResponse.setId(user.getId());
            userSimpleResponse.setName(user.getName());
            userSimpleResponse.setIsActive(user.getIsActive());
            userSimpleResponse.setUsername(user.getUsername());
            userSimpleResponses.add(userSimpleResponse);

        }
        return ResponseEntity.ok(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS,userSimpleResponses));
    }

    @ReadOnlyProperty
    public ResponseEntity<APIResponse> getUserById(HttpServletRequest req, Long userId) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body(APIResponseBuilder.buildExceptionResponse(ExceptionMessages.USER_ID_NOT_FOUND));
        }
        User user = userOptional.get();
        UserSimpleResponse userSimpleResponse = new UserSimpleResponse();
        userSimpleResponse.setId(user.getId());
        userSimpleResponse.setName(user.getName());
        userSimpleResponse.setIsActive(user.getIsActive());
        userSimpleResponse.setUsername(user.getUsername());

        return ResponseEntity.ok(APIResponseBuilder.buildResponse(ResultMessages.API_SUCCESS, userSimpleResponse));
    }
}
