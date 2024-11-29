/**
 * ClassName: AppException
 * Package: com.shane.authservice.exception
 * Description: Custom exception for application-specific errors
 *
 * @Author Shane Liu
 * @Create 2024/11/28 20:10
 * @Version 1.0
 */

package com.shane.authservice.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    private final HttpStatus status;

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
