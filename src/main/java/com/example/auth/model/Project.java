package com.example.auth.model;

import com.example.auth.constants.Constants;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = Constants.PROJECTS)
@Data
public class Project extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false, updatable = false)
    private Long projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    // ✅ Project Manager
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    // ✅ Many-to-Many: Employees working on this project
    @ManyToMany
    @JoinTable(
            name = "project_employee",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> employees;

    // ✅ One Project -> Many Tasks
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Task> tasks;

    // Getters and Setters
}

