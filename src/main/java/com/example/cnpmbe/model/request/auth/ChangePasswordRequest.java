package com.example.cnpmbe.model.request.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequest {

    private String oldPassword;

    private String newPassword;
}
