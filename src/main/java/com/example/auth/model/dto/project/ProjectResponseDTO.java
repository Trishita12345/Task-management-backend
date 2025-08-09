package com.example.auth.model.dto.project;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectResponseDTO {
    private Long projectId;
    private String name;
    private String details;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private EmployeeSummaryDTO manager;    // ✅ full object, not just ID
    private EmployeeSummaryDTO createdBy;
    private EmployeeSummaryDTO updatedBy;

    private List<EmployeeSummaryDTO> employees; // ✅ list of employees
}
