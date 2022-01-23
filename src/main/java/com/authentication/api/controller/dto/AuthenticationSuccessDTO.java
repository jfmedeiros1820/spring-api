package com.authentication.api.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationSuccessDTO extends ResponseDTO {

    String token;

    public AuthenticationSuccessDTO(Boolean error, String message, String token) {
        super(error, message);
        this.token = token;
    }
}
