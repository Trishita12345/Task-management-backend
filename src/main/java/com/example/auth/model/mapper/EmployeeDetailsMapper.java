package com.example.auth.model.mapper;

import com.example.auth.model.Permission;
import com.example.auth.model.dto.employee.EmployeeDetailsResponseDTO;
import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import com.example.auth.model.Employee;

public class EmployeeDetailsMapper {
    public static EmployeeSummaryDTO toEmployeeSummary(Employee employee) {
        if (employee == null) return null;

        EmployeeSummaryDTO dto = new EmployeeSummaryDTO();
        dto.setEmployeeId(employee.getId());
        dto.setFirstName(employee.getFirstname());
        dto.setLastName(employee.getLastname());
        dto.setEmail(employee.getEmail());
        dto.setProfileImage(employee.getProfileImage());
        return dto;
    }
    public static EmployeeDetailsResponseDTO toEmployeeDetails(Employee employee) {
        if (employee == null) return null;

        return EmployeeDetailsResponseDTO.builder()
                .empId(employee.getId())
                .firstname(employee.getFirstname())
                .lastname(employee.getLastname())
                .email(employee.getEmail())
                .profileImage(employee.getProfileImage())
                .role(employee.getRole().getName())
                .permissions(employee.getRole().getPermissions().stream().map(Permission::getName).toList())
                .build();
    }
}
