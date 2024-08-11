package ru.alex.taskmanagementsystem.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.alex.taskmanagementsystem.dto.UserLoginDto;
import ru.alex.taskmanagementsystem.dto.UserRegisterDto;
import ru.alex.taskmanagementsystem.service.UserService;
import ru.alex.taskmanagementsystem.util.JwtUtil;

import java.util.Map;

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
    public Map<String, String> performRegistration(@RequestBody UserRegisterDto user) {
//        TODO заменить Map<> на сущность для ответа
        userService.register(user);
        String token = jwtUtil.generateToken(user.email());
        return Map.of("jwt-token", token);
    }

//    TODO заменить везде Map<> на нормальный ответ
    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody UserLoginDto user) {
        var authToken = new UsernamePasswordAuthenticationToken(user.email(), user.password());
        try {
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            return Map.of("message","Incorrect credentials");
        }
        String token = jwtUtil.generateToken(user.email());
        return Map.of("jwt-token",token);
    }
}
