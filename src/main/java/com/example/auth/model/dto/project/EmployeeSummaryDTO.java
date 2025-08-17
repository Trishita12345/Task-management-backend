package com.example.auth.model.dto.project;

import com.example.auth.model.dto.role.RoleResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EmployeeSummaryDTO {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String profileImage;
    private RoleResponseDTO role;
}
