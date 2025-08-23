package com.example.auth.model.mapper;

import com.example.auth.model.Permission;
import com.example.auth.model.dto.employee.EmployeeDetailsResponseDTO;
import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import com.example.auth.model.Employee;
import com.example.auth.model.dto.role.RoleResponseDTO;
import org.springframework.beans.BeanUtils;

public class EmployeeDetailsMapper {
    public static EmployeeSummaryDTO toEmployeeSummary(Employee employee) {
        if (employee == null) return null;
        RoleResponseDTO roleResponseDTO = (RoleResponseDTO.builder()
                .roleId(employee.getRole().getId())
                .name(employee.getRole().getName()).build());
        EmployeeSummaryDTO dto = new EmployeeSummaryDTO();
        dto.setEmployeeId(employee.getId());
        dto.setFirstName(employee.getFirstname());
        dto.setLastName(employee.getLastname());
        dto.setEmail(employee.getEmail());
        dto.setRole(roleResponseDTO);
        dto.setProfileImage(employee.getProfileImage());
        return dto;
    }
    public static EmployeeDetailsResponseDTO toEmployeeDetails(Employee employee) {
        EmployeeDetailsResponseDTO dto = new EmployeeDetailsResponseDTO();
        BeanUtils.copyProperties(toEmployeeSummary(employee), dto); // copies base fields
        dto.setPermissions(employee.getRole().getPermissions().stream().map(Permission::getName).toList());
        return dto;
    }
}
