package com.example.cnpmbe.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class AuthenInfoResponse {

    private Long userId;
    private String username;
    private String token;
    private String refreshToken;
    private Instant expiredIn;
}
