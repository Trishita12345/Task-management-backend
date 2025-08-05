package com.example.auth.model;

import com.example.auth.util.SecurityUtil;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class AuditEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private Employee createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private Employee updatedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.createdBy = SecurityUtil.getCurrentEmployee();
        this.updatedBy = SecurityUtil.getCurrentEmployee();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = SecurityUtil.getCurrentEmployee();
    }

    // getters/setters
}

