package com.example.auth.model.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProjectAddRequestDTO {

        @NotBlank
        @Size(max = 30, message = "Project name must be at most 30 characters")
        private String name;

        @NotBlank
        @Size(max = 500, message = "Details must be at most 500 characters")
        private String details;

        @NotNull
        private UUID managerId;

        private List<UUID> employeeIds;

}
