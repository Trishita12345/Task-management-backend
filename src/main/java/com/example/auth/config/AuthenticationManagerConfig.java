package com.example.auth.config;

import com.example.auth.provider.JWTAuthenticationProvider;
import com.example.auth.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@Configuration
public class AuthenticationManagerConfig {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private JWTAuthenticationProvider jwtAuthenticationProvider;


    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(employeeService);
        provider.setPasswordEncoder(encoder());
        return provider;
    }

    @Bean
    AuthenticationManager getAuthenticationManager() {
        return new ProviderManager(List.of(daoAuthenticationProvider(), jwtAuthenticationProvider));
    }



}
