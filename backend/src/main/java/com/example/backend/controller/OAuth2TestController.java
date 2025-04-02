package com.example.backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class OAuth2TestController {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/public")
    public Map<String, String> publicEndpoint() {
        return Collections.singletonMap("message", "This is a public endpoint");
    }

    @GetMapping("/private")
    public Map<String, String> privateEndpoint(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("message", 
            "This is a private endpoint. Hello, " + principal.getAttribute("name"));
    }
} 