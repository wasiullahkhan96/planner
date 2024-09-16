package com.example.planner.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.planner.exceptions.UsernameAlreadyExistsException;
import com.example.planner.model.User;
import com.example.planner.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("Username '" + username + "' is already taken.");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Encrypt password
        return userRepository.save(user);
    }

    public User authenticateUser(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new Exception("Invalid credentials");
        }
    }
}
