package com.authentication.api.controller;

import com.authentication.api.controller.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public UserDTO getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new UserDTO(Boolean.FALSE, authentication.getName());
    }
}
