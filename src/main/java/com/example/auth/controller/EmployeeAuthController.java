package com.example.auth.controller;

import com.example.auth.model.Employee;
import com.example.auth.model.dto.auth.RegisterRequestDto;
import com.example.auth.model.dto.employee.EmployeeDetailsResponseDTO;
import com.example.auth.model.mapper.EmployeeDetailsMapper;
import com.example.auth.service.IEmployeeService;
import com.example.auth.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


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
    @Operation(summary = "Register new User")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        log.info("Registering user: {}", registerRequestDto);
        registerRequestDto.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        employeeService.saveUser(registerRequestDto);
        return ResponseEntity.ok("User registered successfully.");
    }

    @GetMapping("/my-profile")
    @Operation(summary = "Get User Details of logged in User")
    public ResponseEntity<EmployeeDetailsResponseDTO> getMyProfile(){
        Employee employee = SecurityUtil.getCurrentEmployee();
        return ResponseEntity.ok(EmployeeDetailsMapper.toEmployeeDetails(employee));
    }
}
