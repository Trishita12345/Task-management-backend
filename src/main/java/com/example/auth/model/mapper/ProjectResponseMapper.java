package com.example.auth.model.mapper;


import com.example.auth.dto.project.EmployeeSummaryDTO;
import com.example.auth.dto.project.ProjectResponseDTO;
import com.example.auth.dto.project.TaskSummaryDTO;
import com.example.auth.model.Employee;
import com.example.auth.model.Project;
import com.example.auth.model.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProjectResponseMapper {

    public ProjectResponseDTO toResponse(Project project) {
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
            dto.setEmployees(project.getEmployees().stream()
                    .map(this::toEmployeeSummary)
                    .toList());
        }else {
            dto.setEmployees(new ArrayList<>());
        }

        // ✅ Map tasks
        if (project.getTasks() != null) {
            dto.setTasks(project.getTasks().stream()
                    .map(this::toTaskSummary)
                    .toList());
        } else {
            dto.setTasks(new ArrayList<>());
        }

        return dto;
    }

    private EmployeeSummaryDTO toEmployeeSummary(Employee employee) {
        if (employee == null) return null;

        EmployeeSummaryDTO dto = new EmployeeSummaryDTO();
        dto.setEmployeeId(employee.getId());
        dto.setFirstName(employee.getFirstname());
        dto.setLastName(employee.getLastname());
        dto.setEmail(employee.getEmail());
        return dto;
    }

    private TaskSummaryDTO toTaskSummary(Task task) {
        if (task == null) return null;

        TaskSummaryDTO dto = new TaskSummaryDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTaskName(task.getTaskName());
        dto.setTaskDescription(task.getTaskDescription());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus().name());
        dto.setStartDate(task.getStartDate());
        dto.setEndDate(task.getEndDate());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        return dto;
    }
}

