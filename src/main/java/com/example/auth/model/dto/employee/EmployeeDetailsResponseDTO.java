package com.example.auth.model.dto.employee;

import com.example.auth.model.dto.role.RoleResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class EmployeeDetailsResponseDTO {
    private UUID empId;
    private String firstname;
    private String lastname;
    private String email;
    private String profileImage;
    private RoleResponseDTO role;
    private List<String> permissions;
}
