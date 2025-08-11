package com.example.auth.service.impl;

import com.example.auth.constants.Constants;
import com.example.auth.model.Permission;
import com.example.auth.model.Role;
import com.example.auth.model.dto.auth.RegisterRequestDto;
import com.example.auth.repository.IPermissionRepository;
import com.example.auth.repository.IRoleRepository;
import com.example.auth.service.IEmployeeService;
import com.example.auth.service.ISeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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
        // Role creation
        Constants.DEFAULT_ROLES.stream()
                .filter(role -> !roleRepository.existsByName(role))
                .map(Role::new)
                .forEach(roleRepository::save);
        // permission creation
        Set<Permission> permissions = Constants.DEFAULT_PERMISSIONS.stream()
                .filter(permissionName -> !permissionRepository.existsByName(permissionName))
                .map(Permission::new)
                .map(permissionRepository::save)
                .collect(Collectors.toSet());
        try {
            // update SUPER_ADMIN role
            Role SUPER_ADMIN_ROLE = roleRepository.findByName(Constants.SUPER_ADMIN).orElseGet(() -> null);
            if(SUPER_ADMIN_ROLE != null){
                SUPER_ADMIN_ROLE.getPermissions().addAll(permissions);
                roleRepository.save(SUPER_ADMIN_ROLE);
            }
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

