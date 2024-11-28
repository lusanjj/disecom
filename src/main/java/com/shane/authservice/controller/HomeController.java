/**
 * ClassName: HomeController
 * Package: com.shane.authservice.controller
 * Description: Handles requests to the home endpoint
 *
 * @Author Shane Liu
 * @Create 2024/11/28 15:35
 * @Version 1.0
 */

package com.shane.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the Authentication Service!";
    }
}
