package com.example.cnpmbe.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleResponse {

    private Long id;

    private String username;

    private String name;

    private Boolean isActive;
}