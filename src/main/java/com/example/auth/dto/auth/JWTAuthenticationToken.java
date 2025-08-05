package com.example.auth.dto.auth;

import com.example.auth.model.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;
    private final Object principal; // can store username or UserDetails

    // Constructor for unauthenticated token (when only JWT string is available)
    public JWTAuthenticationToken(String token) {
        super(null);
        this.token = token;
        this.principal = null;
        setAuthenticated(false);
    }

    // Constructor for authenticated token (with user details)
    public JWTAuthenticationToken(Employee employee){
        super(employee != null ?
                employee.getAuthorities() : null);
        this.principal = employee;
        setAuthenticated(true);
        this.token = null;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
