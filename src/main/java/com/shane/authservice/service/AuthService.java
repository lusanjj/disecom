/**
 * ClassName: AuthService
 * Package: com.shane.authservice.service
 * Description: Business logic for user authentication
 *
 * @Author Shane Liu
 * @Create 2024/11/28 16:40
 * @Version 1.1
 */

package com.shane.authservice.service;

import com.shane.authservice.entity.User;
import com.shane.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Username or email already exists!";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return "Login successful!";
            } else {
                return "Invalid credentials!";
            }
        }
        return "User not found!";
    }
}
