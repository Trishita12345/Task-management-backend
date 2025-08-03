package com.example.auth.provider;

import com.example.auth.dto.JWTAuthenticationToken;
import com.example.auth.service.IEmployeeService;
import com.example.auth.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private IEmployeeService userService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String token = ((JWTAuthenticationToken) authentication).getCredentials().toString();
        String username = jwtUtil.validateAndExtractUsername(token);
        if(username == null){
            throw new BadCredentialsException("Invalid token");
        }
        UserDetails userDetails = userService.loadUserByUsername(username);
        return new JWTAuthenticationToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

