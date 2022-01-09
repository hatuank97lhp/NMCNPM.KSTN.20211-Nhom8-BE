package com.example.cnpmbe.model.request.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Password must not be null")
    private String password;

    private String name;
}

