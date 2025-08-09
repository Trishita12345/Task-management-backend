package com.example.auth.model.dto.role;

import com.example.auth.model.dto.common.SelectOptionDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class RoleAddUpdateResponseDTO {
    private UUID roleId;
    private String name;
    private List<SelectOptionDTO<Long>> permissions;
}
