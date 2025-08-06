package com.example.auth.util;

import com.example.auth.constants.Constants;
import com.example.auth.model.Employee;
import com.example.auth.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    @Autowired
    private IEmployeeRepository employeeRepository;

    public static Employee getCurrentEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException(Constants.UNAUTHORIZED_NO_AUTHENTICATED_USER_FOUND);
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof Employee employee) {
            return employee;
        }
        throw new RuntimeException(Constants.UNAUTHORIZED_NO_AUTHENTICATED_USER_FOUND);
    }
}

