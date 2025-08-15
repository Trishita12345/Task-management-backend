package com.example.auth.model.mapper;

import com.example.auth.model.Role;
import com.example.auth.model.dto.role.RoleAddUpdateDTO;
import com.example.auth.model.dto.role.RoleAddUpdateResponseDTO;

import java.util.ArrayList;

public class RoleAddUpdateDtoMapper {
    public static RoleAddUpdateResponseDTO toRoleAddUpdateResponseDto(Role role) {
        RoleAddUpdateResponseDTO dto = RoleAddUpdateResponseDTO.builder()
                .roleId(role.getId())
                .name(role.getName()).build();

        // ✅ Map permissions
        if (role.getPermissions() != null) {
            dto.setPermissions(role.getPermissions().stream()
                    .map(permission-> permission.getValue())
                    .toList());
        } else {
            dto.setPermissions(new ArrayList<>());
        }

        return dto;
    }
}
