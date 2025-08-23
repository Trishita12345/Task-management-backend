package com.example.auth.model.dto.employee;

import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeDetailsResponseDTO extends EmployeeSummaryDTO {
    private List<String> permissions;
}
