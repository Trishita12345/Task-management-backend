package com.example.auth.model.dto.role;

import com.example.auth.model.dto.common.SelectOptionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class RoleAddUpdateResponseDTO {
    private UUID roleId;
    private String name;
    private List<SelectOptionDTO<UUID>> permissions;
}
