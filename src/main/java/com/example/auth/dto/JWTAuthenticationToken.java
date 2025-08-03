package com.example.auth.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;

    public JWTAuthenticationToken(String token) {
        super(null);
        this.token = token;
        setAuthenticated(false);
    }

    public JWTAuthenticationToken(UserDetails userDetails){
        super(userDetails.getAuthorities());
        setAuthenticated(true);
        this.token = null;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}