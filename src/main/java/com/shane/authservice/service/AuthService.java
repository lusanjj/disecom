/**
 * ClassName: AuthService
 * Package: com.shane.authservice.service
 * Description: Business logic for user authentication
 *
 * @Author Shane Liu
 * @Create 2024/11/28 19:00
 * @Version 1.2
 */

package com.shane.authservice.service;

import com.shane.authservice.entity.User;
import com.shane.authservice.repository.UserRepository;
import com.shane.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Generate and return JWT token
                return jwtUtil.generateToken(username);
            } else {
                return "Invalid credentials!";
            }
        }
        return "User not found!";
    }
}
