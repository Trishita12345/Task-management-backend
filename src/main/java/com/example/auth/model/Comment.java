package com.example.auth.model;

import com.example.auth.model.Employee;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class Comment extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, updatable = false)
    private Long commentId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    // ✅ Belongs to Task
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    // ✅ Written by Employee
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Getters and Setters
}
