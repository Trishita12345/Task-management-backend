package com.example.auth.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.auth.model.Role;
import com.example.auth.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.auth.dto.RegisterRequestDto;
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
        return employeeRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public Employee saveUser(RegisterRequestDto registerRequestDto) {
        Role role = roleRepository.findByName("EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("Role not present in DB"));
        Employee employee = new Employee();
        employee.setUsername(registerRequestDto.getUsername());
        employee.setPassword(registerRequestDto.getPassword());
        employee.setRole(role);
        if (employeeRepository.findByUsername(employee.getUsername()).isEmpty()) {
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("User Already Exists");
        }
    }
    
}
