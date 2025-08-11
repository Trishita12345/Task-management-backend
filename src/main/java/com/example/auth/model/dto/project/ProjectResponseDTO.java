package com.example.auth.model.dto.project;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProjectResponseDTO {
    private UUID projectId;
    private String name;
    private String details;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private EmployeeSummaryDTO manager;    // ✅ full object, not just ID
    private EmployeeSummaryDTO createdBy;
    private EmployeeSummaryDTO updatedBy;

    private List<EmployeeSummaryDTO> employees; // ✅ list of employees

    }

