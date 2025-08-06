package com.example.auth.service;

import java.util.NoSuchElementException;
import java.util.UUID;

import com.example.auth.constants.Constants;
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
                .orElseThrow(() -> new NoSuchElementException(Constants.USER_NOT_FOUND));
    }



    @Override
    public Employee saveUser(RegisterRequestDto registerRequestDto) {
        Role role = roleRepository.findByName(Constants.DEFAULT_EMPLOYEE_TYPE)
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

    @Override
    public Employee getEmployeeByEmailId(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(Constants.USER_NOT_FOUND));
    }

}
