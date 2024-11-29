/**
 * ClassName: JwtUtil
 * Package: com.shane.authservice.util
 * Description: Utility class for generating and validating JWT tokens
 *
 * @Author Shane Liu
 * @Create 2024/11/28 21:00
 * @Version 1.2
 */

package com.shane.authservice.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private final int ACCESS_TOKEN_VALIDITY = 1000 * 60 * 15; // 15 minutes
    private final int REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24; // 24 hours

    /**
     * Generate an access token for the given username
     *
     * @param username the username for which the token is generated
     * @return a signed JWT access token
     */
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Generate a refresh token for the given username
     *
     * @param username the username for which the token is generated
     * @return a signed JWT refresh token
     */
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Extract the username from the token
     *
     * @param token the JWT token
     * @return the username
     */
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validate the token against the username
     *
     * @param token    the JWT token
     * @param username the username to validate
     * @return true if the token is valid
     */
    public boolean validateToken(String token, String username) {
        return (extractUsername(token).equals(username) && !isTokenExpired(token));
    }

    /**
     * Check if the token is expired
     *
     * @param token the JWT token
     * @return true if the token is expired
     */
    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
