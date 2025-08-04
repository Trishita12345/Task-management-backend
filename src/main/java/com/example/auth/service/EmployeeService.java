package com.example.auth.service;

import java.util.NoSuchElementException;

import com.example.auth.dto.auth.JWTAuthenticationToken;
import com.example.auth.dto.auth.RegisterRequestDto;
import com.example.auth.model.Role;
import com.example.auth.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.auth.model.Employee;
import com.example.auth.repository.IEmployeeRepository;

@Service
public class EmployeeService implements IEmployeeService {
    
    @Autowired
    private IEmployeeRepository employeeRepository;
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public Employee saveUser(RegisterRequestDto registerRequestDto) {
        Role role = roleRepository.findByName("EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("Role not present in DB"));
        Employee employee = new Employee();
        employee.setFirstname(registerRequestDto.getFirstname());
        employee.setLastname(registerRequestDto.getLastname());
        employee.setEmail(registerRequestDto.getEmail());
        employee.setProfileImage(registerRequestDto.getProfileImage());
        employee.setPassword(registerRequestDto.getPassword());
        employee.setRole(role);
        if (employeeRepository.findByEmail(employee.getUsername()).isEmpty()) {
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("User Already Exists");
        }
    }
    
}
