package ru.alex.taskmanagementsystem.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.alex.taskmanagementsystem.dto.AuthResponse;
import ru.alex.taskmanagementsystem.dto.ErrorResponse;
import ru.alex.taskmanagementsystem.dto.UserLoginDto;
import ru.alex.taskmanagementsystem.dto.UserRegisterDto;
import ru.alex.taskmanagementsystem.service.UserService;
import ru.alex.taskmanagementsystem.util.JwtUtil;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> performRegistration(@RequestBody UserRegisterDto user) {
        userService.register(user);
        String token = jwtUtil.generateToken(user.email());
        return new ResponseEntity<>(new AuthResponse(token), CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody UserLoginDto user) {
        var authToken = new UsernamePasswordAuthenticationToken(user.email(), user.password());
        try {
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ErrorResponse("Incorrect credentials"), BAD_REQUEST);
        }
        String token = jwtUtil.generateToken(user.email());
        return new ResponseEntity<>(new AuthResponse(token), OK);
    }
}