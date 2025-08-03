package com.example.auth.config;

import com.example.auth.filter.JWTAuthenticationFilter;
import com.example.auth.filter.JWTRefreshFilter;
import com.example.auth.filter.JWTValidationFilter;
import com.example.auth.util.PublicEndPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JWTValidationFilter jwtValidationFilter;
    @Autowired
    private JWTRefreshFilter jwtRefreshFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // for h2-console
        .authorizeHttpRequests(auth -> auth
                .requestMatchers(PublicEndPoints.patterns()).permitAll()
                .anyRequest().authenticated()
        )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtValidationFilter, JWTAuthenticationFilter.class)
                .addFilterAfter(jwtRefreshFilter, JWTValidationFilter.class);
        return http.build();
    }
}

