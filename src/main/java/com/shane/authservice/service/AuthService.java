/**
 * ClassName: AuthService
 * Package: com.shane.authservice.service
 * Description: Business logic for user authentication
 *
 * @Author Shane Liu
 * @Create 2024/11/28 23:15
 * @Version 1.3
 */

package com.shane.authservice.service;

import com.shane.authservice.entity.User;
import com.shane.authservice.exception.AppException;
import com.shane.authservice.repository.UserRepository;
import com.shane.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new AppException("Username already exists!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new AppException("Email already exists!", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    public Map<String, String> loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new AppException("User not found!", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AppException("Invalid credentials!", HttpStatus.UNAUTHORIZED);
        }

        // Generate tokens with role included
        String accessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), user.getRole());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }
//    generateResetToken for the new password
    public void generateResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("User with email not found!", HttpStatus.NOT_FOUND));

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour

        userRepository.save(user);

        // TODO: Integrate email service to send the reset token
        System.out.println("Reset Token: " + resetToken); // Placeholder for sending email
    }

//    for resetting the password

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new AppException("Invalid or expired reset token!", HttpStatus.BAD_REQUEST));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new AppException("Reset token has expired!", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Invalidate token after use
        user.setResetTokenExpiry(null);

        userRepository.save(user);
    }

//  changePassword
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AppException("Incorrect old password!", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    public void invalidateToken(String refreshToken) {
        // Example: Add logic to remove or blacklist the token
        System.out.println("Invalidated token: " + refreshToken);
        // TODO: Implement token blacklist if storing tokens server-side
    }





}
