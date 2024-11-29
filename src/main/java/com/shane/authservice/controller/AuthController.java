/**
 * ClassName: AuthController
 * Package: com.shane.authservice.controller
 * Description: Manages authentication-related endpoints
 *
 * @Author Shane Liu
 * @Create 2024/11/28 22:00
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

    /**
     * Register a new user
     *
     * @param user the user details to register
     * @return a success message
     */
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return authService.registerUser(user);
    }

    /**
     * Login a user and return tokens
     *
     * @param loginRequest the login request payload
     * @return a map containing access and refresh tokens
     */
    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
    }

    /**
     * Refresh access token using the refresh token
     *
     * @param request contains the refresh token
     * @return a map containing the new access token
     */
    @PostMapping("/refresh-token")
    public Map<String, String> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        String username = jwtUtil.extractUsername(refreshToken);

        if (!jwtUtil.validateToken(refreshToken, username)) {
            throw new RuntimeException("Invalid or expired refresh token!");
        }

        String newAccessToken = jwtUtil.generateAccessToken(username);
        return Map.of("accessToken", newAccessToken);
    }

    /**
     * Fetch the user's profile
     *
     * @return a profile message
     */
    @GetMapping("/profile")
    public String getProfile() {
        return "This is your profile!";
    }
}
