package com.example.auth.model.dto.project;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ProjectAddRequestDTO {

        @NotBlank
        @Size(max = 30, message = "Project name must be at most 30 characters")
        private String name;

        @NotBlank
        @Size(max = 100, message = "Details must be at most 100 characters")
        private String details;

        @NotNull
        private UUID managerId;

}
