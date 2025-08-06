package com.example.auth.util;

import com.example.auth.constants.Constants;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class JWTUtil {
    private static final Key key = Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(Authentication authentication, String type){
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> permissions = authorities
                .stream()
                .map(GrantedAuthority::getAuthority) // extract only the string
                .toList();
        Map<String, ?> claims = Map.of(Constants.PERMISSIONS, permissions);
        long expiryMinutes = type.equals(Constants.ACCESS) ? Constants.ACCESS_TOKEN_EXPIRATION_TIME : Constants.REFRESH_TOKEN_EXPIRATION_TIME;
        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .claim(Constants.TYPE, type)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiryMinutes * 60 * 1000))
                .signWith(key)
                .compact();
    }

    public static String validateAndExtractUsername(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            log.info("exception extracting username: "+e.getMessage());
            return null;
        }
    }

    public static String extractJWTTokenFromRequestCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        return token;
    }
}
