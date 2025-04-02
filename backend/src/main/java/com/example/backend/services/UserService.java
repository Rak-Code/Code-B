package com.example.backend.services;



import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Password encoder (ensure to configure as a Bean if needed)
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(User user) {
        // Check if email already exists
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email already in use");
        }
        // Hash the password before saving
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public Optional<User> authenticateUser(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            if(passwordEncoder.matches(rawPassword, user.getPasswordHash())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // Implement methods for forgot password, reset password etc.
}

