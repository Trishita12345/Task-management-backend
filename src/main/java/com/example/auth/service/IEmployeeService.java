package com.example.auth.service;

import com.example.auth.dto.auth.JWTAuthenticationToken;
import com.example.auth.dto.auth.RegisterRequestDto;
import com.example.auth.model.Employee;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface IEmployeeService extends UserDetailsService {
    Employee saveUser(RegisterRequestDto registerRequestDto);
    Employee getEmployeeByEmailId(String email);
} 
