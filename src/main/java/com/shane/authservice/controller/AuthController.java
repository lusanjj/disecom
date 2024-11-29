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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER"); // Default role
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
        System.out.println("Username from Refresh Token: " + username);
        System.out.println("Role from Refresh Token: " + role);

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
