package com.example.auth.filter;

import com.example.auth.constants.Constants;
import com.example.auth.model.dto.auth.JWTAuthenticationToken;
import com.example.auth.util.PublicEndPoints;
import com.example.auth.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JWTValidationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            String path = request.getServletPath();
            return PublicEndPoints.isPublic(path);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // String token = JWTUtil.extractJWTTokenFromRequestCookie(request, Constants.ACCESS);
        String token = extractJWTTokenFromRequest(request);
        if(token != null){
            JWTAuthenticationToken jwtAuthenticationToken = new JWTAuthenticationToken(token);
            try{
                Authentication authResult = authenticationManager.authenticate(jwtAuthenticationToken);
                if(authResult.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(authResult);
                    filterChain.doFilter(request, response);
                }
            } catch (Exception ex){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid credentials");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid credentials");
        }
    }

    private String extractJWTTokenFromRequest (HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
