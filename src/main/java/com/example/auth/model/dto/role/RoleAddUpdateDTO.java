package com.example.auth.model.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RoleAddUpdateDTO {
    @NotBlank
    private String name;
    @NotNull
    private List<UUID> permissions;
}
