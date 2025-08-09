package com.example.auth.service.impl;

import com.example.auth.constants.Constants;
import com.example.auth.model.Employee;
import com.example.auth.model.Role;
import com.example.auth.model.dto.auth.RegisterRequestDto;
import com.example.auth.repository.IEmployeeRepository;
import com.example.auth.repository.IRoleRepository;
import com.example.auth.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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


    @Transactional
    @Override
    public Employee saveUser(RegisterRequestDto registerRequestDto) {
        return createEmployeeByRole(registerRequestDto, Constants.DEFAULT_EMPLOYEE_TYPE);
    }

    private Employee createEmployeeByRole(RegisterRequestDto registerRequestDto, String employeeType) {
        Role role = roleRepository.findByName(employeeType)
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

    @Transactional
    @Override
    public Employee saveAdmin(RegisterRequestDto registerRequestDto) {
        return createEmployeeByRole(registerRequestDto, Constants.SUPER_ADMIN);
    }

}
