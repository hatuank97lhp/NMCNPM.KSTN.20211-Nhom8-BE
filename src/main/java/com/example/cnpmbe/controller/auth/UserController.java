package com.example.cnpmbe.controller.auth;

import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.service.AuthenService;
import com.example.cnpmbe.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/v1/accounts")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenService authenService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllUsers(HttpServletRequest req) {
        ResponseEntity<APIResponse> userResponse = userService.getAllUsers(req);
        return userResponse;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse> getUserById(HttpServletRequest req,
                                                   @PathVariable Long userId) {
        ResponseEntity<APIResponse> response = userService.getUserById(req, userId);
        return response;
    }
}