package com.example.auth.filter;

import com.example.auth.dto.JWTAuthenticationToken;
import com.example.auth.util.JWTUtil;
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

@Slf4j
@Component
public class JWTRefreshFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       log.info("JWTRefreshFilter");
        if (!request.getServletPath().equals("/refresh-token")) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = extractJwtFromRequest(request);
        if (refreshToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        JWTAuthenticationToken authenticationToken = new JWTAuthenticationToken(refreshToken);
        Authentication authResult = authenticationManager.authenticate(authenticationToken);
        if (authResult.isAuthenticated()) {
            String newToken = jwtUtil.generateToken(authResult.getName(),authResult.getAuthorities(), 15L); //15min
            response.setHeader("Authorization", "Bearer " + newToken);
            String refreshTokenNew = jwtUtil.generateToken(authResult.getName(),authResult.getAuthorities(), (long)7*24*60);
            Cookie refreshCookie = new Cookie("refreshToken", refreshTokenNew);
            refreshCookie.setHttpOnly(true); //prevent javascript from accessing it
            refreshCookie.setSecure(false); // sent only over HTTPS
            refreshCookie.setPath("/refresh-token"); // Cookie available only for refresh endpoint
            refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days expiry
            response.addCookie(refreshCookie);
        }
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        String refreshToken = null;
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                refreshToken = cookie.getValue();
            }
        }
        return refreshToken;
    }
}
