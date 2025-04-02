package com.example.backend.controller;

import com.example.backend.dto.AuthResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.model.User;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // User Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(new AuthResponse(true, "User registered successfully", registeredUser));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(false, e.getMessage(), null));
        }
    }

    // User Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword())
                .map(user -> ResponseEntity.ok(new AuthResponse(true, "Login successful", user)))
                .orElse(ResponseEntity.badRequest().body(new AuthResponse(false, "Invalid email or password", null)));
    }

    // Placeholder for forgot password & reset password endpoints
}


