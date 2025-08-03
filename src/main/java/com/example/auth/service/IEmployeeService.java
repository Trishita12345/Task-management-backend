package com.example.auth.service;

import com.example.auth.model.Employee;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.auth.dto.RegisterRequestDto;

public interface IEmployeeService extends UserDetailsService {
    Employee saveUser(RegisterRequestDto registerRequestDto);
} 
