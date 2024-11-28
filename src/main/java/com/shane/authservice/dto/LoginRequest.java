/**
 * ClassName: LoginRequest
 * Package: com.shane.authservice.dto
 * Description: Data Transfer Object for login requests
 *
 * @Author Shane Liu
 * @Create 2024/11/28 18:50
 * @Version 1.0
 */

package com.shane.authservice.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
