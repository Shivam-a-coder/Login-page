package com.example.MyPage.controller;

import com.example.MyPage.DTO.LoginRequest;
import com.example.MyPage.DTO.SignUpRequest;
import com.example.MyPage.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody @Valid SignUpRequest request)
    {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        return userService.authenticateUser(request);
    }
}
