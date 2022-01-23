package com.authentication.api.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends ResponseDTO{

    String username;

    public UserDTO(Boolean error, String username) {
        super(error, null);
        this.username = username;
    }
}
