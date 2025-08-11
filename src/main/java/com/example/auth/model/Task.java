package com.example.auth.model;

import com.example.auth.constants.Constants;
import com.example.auth.model.enums.Priority;
import com.example.auth.model.enums.TaskStatus;
import com.example.auth.model.enums.TaskType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = Constants.TASKS)

public class Task extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "task_id", nullable = false, updatable = false)
    private UUID taskId;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "task_description", columnDefinition = "TEXT")
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority = Priority.P1;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status = TaskStatus.TODO;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TaskType type = TaskType.STORY;

    // ✅ Assigned to Employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private Employee assignedTo;

    // ✅ Managed by Employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managed_by")
    private Employee managedBy;

    // ✅ Belongs to Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;


    // ✅ One Task -> Many Comments
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Comment> comments;

    // Getters and Setters
}
