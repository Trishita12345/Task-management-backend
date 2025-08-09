package com.example.auth.provider;

import com.example.auth.model.dto.auth.JWTAuthenticationToken;
import com.example.auth.model.Employee;
import com.example.auth.service.IEmployeeService;
import com.example.auth.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private IEmployeeService employeeService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String token = ((JWTAuthenticationToken) authentication).getCredentials().toString();
        String username = JWTUtil.validateAndExtractUsername(token);
        if(username == null){
            throw new BadCredentialsException("Invalid token");
        }
        Employee employee = employeeService.getEmployeeByEmailId(username);
        return new JWTAuthenticationToken(employee);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

