package com.example.auth.controller;

import com.example.auth.dto.auth.RegisterRequestDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.service.IEmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequestMapping(path = "/auth")
public class EmployeeAuthController {
    
    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user-register")
    @SecurityRequirements()
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        log.info("Registering user: {}", registerRequestDto);
        registerRequestDto.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        employeeService.saveUser(registerRequestDto);
        return ResponseEntity.ok("User registered successfully.");
    }
    
}
