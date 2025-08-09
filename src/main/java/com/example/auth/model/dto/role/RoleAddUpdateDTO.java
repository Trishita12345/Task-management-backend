package com.example.auth.model.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RoleAddUpdateDTO {
    @NotBlank
    private String name;
    @NotNull
    private List<Long> permissions;
}
