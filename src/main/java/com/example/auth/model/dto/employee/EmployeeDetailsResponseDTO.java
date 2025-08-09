package com.example.auth.model.dto.employee;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class EmployeeDetailsResponseDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String profileImage;
    private String role;
    private List<String> permissions;
}
