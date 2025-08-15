package com.example.auth.filter;

import com.example.auth.constants.Constants;
import com.example.auth.model.dto.auth.JWTAuthenticationToken;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
public class JWTRefreshFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       log.info("JWTRefreshFilter");
        if (!request.getServletPath().equals("/refresh-token")) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = JWTUtil.extractJWTTokenFromRequestCookie(request, Constants.REFRESH);
        System.out.println("refreshToken: "+ refreshToken);
        if (refreshToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        JWTAuthenticationToken authenticationToken = new JWTAuthenticationToken(refreshToken);
        Authentication authResult = authenticationManager.authenticate(authenticationToken);
        if(authResult.isAuthenticated()){
            String token = JWTUtil.generateToken(authResult,  Constants.ACCESS);
           // response.setHeader("Authorization", "Bearer " +token);
            Cookie accessCookie = new Cookie(Constants.ACCESS, token);
            accessCookie.setHttpOnly(false); //javascript can access it
            accessCookie.setSecure(false); // sent only over HTTPS
            accessCookie.setPath("/"); // Cookie available for all endpoints
            accessCookie.setMaxAge(Constants.ACCESS_TOKEN_EXPIRATION_TIME); // 1 days expiry
            response.addCookie(accessCookie);
            //Refresh token
            String refreshTokenNew = JWTUtil.generateToken(authResult,Constants.REFRESH);
            Cookie refreshCookie = new Cookie(Constants.REFRESH, refreshTokenNew);
            refreshCookie.setHttpOnly(true); //prevent javascript from accessing it
            refreshCookie.setSecure(false); // sent only over HTTPS
            refreshCookie.setPath("/refresh-token"); // Cookie available only for refresh endpoint
            refreshCookie.setMaxAge(Constants.REFRESH_TOKEN_EXPIRATION_TIME); // 7 days expiry
            response.addCookie(refreshCookie);
            response.setContentType("application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(),
                    Map.of(
                            "timestamp", LocalDateTime.now().toString(),
                            Constants.MESSAGE, "Logged in Successfully",
                            Constants.ACCESS, token
                    )
            );
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid credentials");
        }
    }
}
