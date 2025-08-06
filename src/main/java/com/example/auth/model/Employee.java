package com.example.auth.model;

import java.util.*;

import com.example.auth.constants.Constants;
import jakarta.persistence.*;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Data
@Table(name = Constants.EMPLOYEES)
//@ToString(exclude = {"assignedTasks", "managedTasks", "createdTasks", "updatedTasks", "createdProjects", "updatedProjects", "createdComments", "updatedComments"})
public class Employee implements UserDetails {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "password", nullable = false)
    private String password;

    // ✅ Self-reference for manager hierarchy
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    // ✅ Many Employees -> One Role
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

//    // ✅ Many-to-Many: Employee works on many Projects
//    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
//    private Set<Project> projects;
//
//    // ✅ Tasks assigned to this employee
//    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY)
//    private Set<Task> assignedTasks;
//
//    // ✅ Tasks managed by this employee
//    @OneToMany(mappedBy = "managedBy", fetch = FetchType.LAZY)
//    private Set<Task> managedTasks;
//
//    // ✅ Tasks created by this employee
//    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
//    private Set<Task> createdTasks;
//
//    // ✅ Tasks updated by this employee
//    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
//    private Set<Task> updatedTasks;
//
//    // ✅ Comments written by this employee
//    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
//    private Set<Project> createdProjects;
//
//    // ✅ Comments updated by this employee
//    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
//    private Set<Project> updatedProjects;
//
//    // ✅ Comments created by this employee (audit)
//    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
//    private Set<Comment> createdComments;
//
//    // ✅ Comments updated by this employee (audit)
//    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
//    private Set<Comment> updatedComments;

    // Getters and Setters

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        role.getPermissions().forEach(permission ->
                authorities.add(new SimpleGrantedAuthority(permission.getName())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }



}




