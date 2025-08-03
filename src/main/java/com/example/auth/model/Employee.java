package com.example.auth.model;

import java.util.*;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Data
@Table(name = "EMPLOYEES")
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // 🔹 Self-referencing relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id") // foreign key column in the same table
    private Employee manager;

    @ManyToOne(fetch = FetchType.EAGER) // each user has exactly one role
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        role.getPermissions().forEach(permission ->
                authorities.add(new SimpleGrantedAuthority(permission.getName())));
        return authorities;
    }
    
}
