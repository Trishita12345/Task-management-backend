package com.example.auth.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JWTUtil {
    private static final String SECRET_KEY = "your-secret-key-your-secret-key-your-secret-key-your-secret-key-your-secret-key";
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));


    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities, Long expiryMinutes){
        List<String> permissions = authorities
                .stream()
                .map(GrantedAuthority::getAuthority) // extract only the string
                .toList();
        Map<String, ?> claims = Map.of("permissions", permissions);
        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiryMinutes * 60 * 1000))
                .signWith(key)
                .compact();
    }

    public String validateAndExtractUsername(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
}
