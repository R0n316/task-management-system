package ru.alex.taskmanagementsystem.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.taskmanagementsystem.dto.AuthDto;
import ru.alex.taskmanagementsystem.service.UserService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody AuthDto user){
        userService.register(user);
        return new ResponseEntity<>(CREATED);
    }
}
