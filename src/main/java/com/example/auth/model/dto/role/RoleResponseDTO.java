package com.example.auth.model.dto.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class RoleResponseDTO {
    private UUID roleId;
    private String name;
}
