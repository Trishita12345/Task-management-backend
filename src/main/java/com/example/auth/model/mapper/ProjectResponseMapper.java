package com.example.auth.model.mapper;

import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import com.example.auth.model.dto.project.ProjectResponseDTO;
import com.example.auth.model.Project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.auth.model.mapper.EmployeeDetailsMapper.toEmployeeSummary;

public class ProjectResponseMapper {

    public static ProjectResponseDTO toResponse(Project project) {
        if (project == null) return null;

        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setProjectId(project.getProjectId());
        dto.setName(project.getName());
        dto.setDetails(project.getDetails());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());
        dto.setCreatedBy(toEmployeeSummary(project.getCreatedBy()));
        dto.setUpdatedBy(toEmployeeSummary(project.getUpdatedBy()));
        // ✅ Map manager, createdBy, updatedBy
        dto.setManager(toEmployeeSummary(project.getManager()));

        // ✅ Map employees
        if (project.getEmployees() != null) {
            List<EmployeeSummaryDTO> employeeSummaryDTOS = new ArrayList<>(project.getEmployees().stream()
                    .map(EmployeeDetailsMapper::toEmployeeSummary)
                    .toList());
            employeeSummaryDTOS.sort(Comparator.comparing(EmployeeSummaryDTO::getEmail));
            dto.setEmployees(employeeSummaryDTOS);
        } else {
            dto.setEmployees(new ArrayList<>());
        }

        return dto;
    }

}

