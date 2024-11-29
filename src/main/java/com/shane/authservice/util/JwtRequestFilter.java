/**
 * ClassName: JwtRequestFilter
 * Package: com.shane.authservice.util
 * Description: Filters incoming requests to validate JWT tokens
 *
 * @Author Shane Liu
 * @Create 2024/11/28 22:30
 * @Version 1.1
 */

package com.shane.authservice.util;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Extract the token
            try {
                username = jwtUtil.extractUsername(jwt);
                String role = jwtUtil.extractRole(jwt); // Extract role from token

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtil.validateToken(jwt, username)) {
                        // Set the user's role as an authority
                        var authorities = List.of(new SimpleGrantedAuthority(role));
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(username, null, authorities);
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Token has expired\"}");
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Invalid token\"}");
                return;
            }
        }

        chain.doFilter(request, response);
    }


}
