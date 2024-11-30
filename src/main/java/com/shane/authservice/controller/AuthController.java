/**
 * ClassName: AuthController
 * Package: com.shane.authservice.controller
 * Description: Manages authentication-related endpoints
 *
 * @Author Shane Liu
 * @Create 2024/11/28 23:20
 * @Version 1.4
 */

package com.shane.authservice.controller;

import com.shane.authservice.entity.User;
import com.shane.authservice.dto.LoginRequest;
import com.shane.authservice.service.AuthService;
import com.shane.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;


//    password
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        authService.generateResetToken(email);
        return "Password reset token sent to your email!";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        authService.resetPassword(token, newPassword);
        return "Password reset successfully!";
    }

    @PatchMapping("/change-password")
    public String changePassword(@RequestBody Map<String, String> request, Principal principal) {
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        authService.changePassword(principal.getName(), oldPassword, newPassword);
        return "Password changed successfully!";
    }

    @PostMapping("/logout")
    public String logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        authService.invalidateToken(refreshToken);
        return "Logged out successfully!";
    }

    //resister
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // Default role
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/refresh-token")
    public Map<String, String> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        // Extract username from the refreshToken
        String username = jwtUtil.extractUsername(refreshToken);

        // Validate the refresh token
        if (!jwtUtil.validateToken(refreshToken, username)) {
            throw new RuntimeException("Invalid or expired refresh token!");
        }

        // Extract role from the refreshToken
        String role = jwtUtil.extractRole(refreshToken);

        // Generate a new accessToken with the extracted role
        String newAccessToken = jwtUtil.generateAccessToken(username, role);

        return Map.of("accessToken", newAccessToken);
    }


    @GetMapping("/profile")
    public String getProfile() {
        return "This is your profile!";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Welcome, Admin!";
    }







}
