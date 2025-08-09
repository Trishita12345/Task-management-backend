package com.example.auth.service.impl;

import com.example.auth.constants.Constants;
import com.example.auth.model.Permission;
import com.example.auth.model.Role;
import com.example.auth.model.dto.auth.RegisterRequestDto;
import com.example.auth.repository.IPermissionRepository;
import com.example.auth.repository.IRoleRepository;
import com.example.auth.service.IEmployeeService;
import com.example.auth.service.ISeedService;
import io.jsonwebtoken.security.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SeedService implements ISeedService {

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IPermissionRepository permissionRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void setupInitialData() {
        Constants.DEFAULT_PERMISSIONS.stream()
                .filter(permissionName -> !permissionRepository.existsByName(permissionName))
                .map(Permission::new)
                .forEach(permissionRepository::save);
        Constants.DEFAULT_ROLES.stream()
                .filter(role -> !roleRepository.existsByName(role))
                .map(Role::new).forEach(roleRepository::save);
        try {
            UserDetails userDetails = employeeService.loadUserByUsername(username);
            if (userDetails != null) return;
        } catch (Exception ex) {
            RegisterRequestDto requestDto = RegisterRequestDto.builder()
                    .email(username)
                    .password(encoder.encode(password))
                    .firstname("ADMIN")
                    .lastname("USER")
                    .build();
            employeeService.saveAdmin(requestDto);
        }
    }
}

