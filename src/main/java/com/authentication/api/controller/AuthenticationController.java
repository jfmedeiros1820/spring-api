package com.authentication.api.controller;

import com.authentication.api.controller.dto.AuthenticationSuccessDTO;
import com.authentication.api.controller.dto.ResponseDTO;
import com.authentication.api.controller.dto.UserCreatedDTO;
import com.authentication.api.model.User;
import com.authentication.api.repository.UserRepository;
import com.authentication.api.service.JwtUserDetailsService;
import com.authentication.api.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    final UserRepository userRepository;
    final AuthenticationManager authenticationManager;
    final JwtUserDetailsService userDetailsService;
    final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestParam("user_name") String username, @RequestParam("password") String password) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if (auth.isAuthenticated()) {
                log.info("Logged In");
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                String token = jwtTokenUtil.generateToken(userDetails);
                AuthenticationSuccessDTO authenticationSuccessDTO = new AuthenticationSuccessDTO(Boolean.FALSE, "Logged In", token);
                return ResponseEntity.ok(authenticationSuccessDTO);
            } else {
                ResponseDTO responseDTO = new ResponseDTO(Boolean.TRUE, "Invalid Credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
            }
        } catch (DisabledException e) {
            e.printStackTrace();
            ResponseDTO responseDTO = new ResponseDTO(Boolean.TRUE, "User id disabled");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        } catch (BadCredentialsException e) {
            ResponseDTO responseDTO = new ResponseDTO(Boolean.TRUE, "Invalid Credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO responseDTO = new ResponseDTO(Boolean.TRUE, "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PostMapping("/register")
    public UserCreatedDTO saveUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole("USER");
        user.setUserName(username);
        userRepository.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(userDetails);
        return new UserCreatedDTO(Boolean.FALSE, "Account created successfully", username, token);
    }
}
