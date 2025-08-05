package com.example.auth.model;

import com.example.auth.model.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "tasks")
public class Task extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "task_id", nullable = false, updatable = false)
    private UUID taskId;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "task_description", columnDefinition = "TEXT")
    private String taskDescription;

    @Column(name = "priority", nullable = false)
    private String priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status = TaskStatus.NEW;

    // ✅ Assigned to Employee
    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private Employee assignedTo;

    // ✅ Managed by Employee
    @ManyToOne
    @JoinColumn(name = "managed_by")
    private Employee managedBy;

    // ✅ Belongs to Project
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;


    // ✅ One Task -> Many Comments
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    // Getters and Setters
}
