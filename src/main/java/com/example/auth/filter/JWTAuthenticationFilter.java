package com.example.auth.filter;

import com.example.auth.constants.Constants;
import com.example.auth.model.dto.auth.LoginRequestDto;
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
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

            Authentication authResult = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if(authResult.isAuthenticated()){
                String token = JWTUtil.generateToken(authResult, Constants.ACCESS);
                //response.setHeader("Authorization", "Bearer " +token);
                Cookie accessCookie = new Cookie(Constants.ACCESS, token);
                accessCookie.setHttpOnly(false); //javascript can access it
                accessCookie.setSecure(false); // sent only over HTTPS
                accessCookie.setPath("/"); // Cookie available for all endpoints
                accessCookie.setMaxAge(Constants.ACCESS_TOKEN_EXPIRATION_TIME); // 1 days expiry
                response.addCookie(accessCookie);
                response.getWriter().write("Logged in successfully");
                //Refresh token
                String refreshToken = JWTUtil.generateToken(authResult, Constants.REFRESH);
                Cookie refreshCookie = new Cookie(Constants.REFRESH, refreshToken);
                refreshCookie.setHttpOnly(true); //prevent javascript from accessing it
                refreshCookie.setSecure(false); // sent only over HTTPS
                refreshCookie.setPath("/refresh-token"); // Cookie available only for refresh endpoint
                refreshCookie.setMaxAge(Constants.REFRESH_TOKEN_EXPIRATION_TIME); // 7 days expiry
                response.addCookie(refreshCookie);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid credentials");
            }
        }
    }


}
