package com.example.auth.dto.project;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeSummaryDTO {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String email;
}
