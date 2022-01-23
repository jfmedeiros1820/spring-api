package com.authentication.api.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreatedDTO extends ResponseDTO{

    String username;

    String token;

    public UserCreatedDTO(Boolean error, String message, String username, String token) {
        super(error, message);
        this.username = username;
        this.token = token;
    }
}
