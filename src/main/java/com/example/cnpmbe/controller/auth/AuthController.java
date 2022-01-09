package com.example.cnpmbe.controller.auth;


import com.example.cnpmbe.common.APIResponse;
import com.example.cnpmbe.model.request.auth.ChangePasswordRequest;
import com.example.cnpmbe.model.request.auth.LoginRequest;
import com.example.cnpmbe.model.request.auth.UserRequest;
import com.example.cnpmbe.service.AuthenService;
import com.example.cnpmbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenService authenService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse> loginByUsername(HttpServletRequest req,
                                                       @RequestBody @Valid LoginRequest loginRequest) {

        ResponseEntity<APIResponse> response = authenService.loginByUsername(req, loginRequest);
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse> createUser(HttpServletRequest req,
                                                  @RequestBody @Valid UserRequest userRequest) {
        ResponseEntity<APIResponse> userResponse = userService.createUser(req, userRequest);
        return userResponse;
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<APIResponse> resetPassword(HttpServletRequest req,
                                                     @RequestBody @Valid ChangePasswordRequest resetPasswordRequest, @PathVariable Long id) {
        ResponseEntity<APIResponse> response = authenService.changePassword(req, resetPasswordRequest, id);
        return response;
    }
}

