/**
 * ClassName: AuthController
 * Package: com.shane.authservice.controller
 * Description: Handles authentication-related requests
 *
 * @Author Shane Liu
 * @Create 2024/11/28 16:35
 * @Version 1.1
 */

package com.shane.authservice.controller;

import com.shane.authservice.dto.LoginRequest;
import com.shane.authservice.entity.User;
import com.shane.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid User user) {
        return authService.registerUser(user);
    }


    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
