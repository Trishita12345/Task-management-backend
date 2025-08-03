package com.example.auth.filter;

import com.example.auth.dto.LoginRequestDto;
import com.example.auth.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("JWTAuthenticationFilter");
        if(!request.getServletPath().equals("/generate-token")){
            filterChain.doFilter(request, response);
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequestDto loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

        if(loginRequestDto == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid request");
            return;
        } else {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());

            Authentication authResult = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if(authResult.isAuthenticated()){
                String token = jwtUtil.generateToken(authResult.getName(), authResult.getAuthorities(), 15L);
                response.setHeader("Authorization", "Bearer " +token);
                response.getWriter().write("Logged in successfully");
                String refreshToken = jwtUtil.generateToken(authResult.getName(), authResult.getAuthorities(), (long)7*24*60);
                Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
                refreshCookie.setHttpOnly(true); //prevent javascript from accessing it
                refreshCookie.setSecure(false); // sent only over HTTPS
                refreshCookie.setPath("/refresh-token"); // Cookie available only for refresh endpoint
                refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days expiry
                response.addCookie(refreshCookie);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid credentials");
            }
        }
    }


}
