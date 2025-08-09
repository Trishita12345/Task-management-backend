package com.example.auth.service;

import com.example.auth.model.dto.common.SelectOptionDTO;
import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import com.example.auth.model.dto.role.RoleAddUpdateDTO;
import com.example.auth.model.dto.role.RoleAddUpdateResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IAdminService {
    List<SelectOptionDTO<UUID>> getRoles();
    List<SelectOptionDTO<Long>> getPermissions();
    RoleAddUpdateResponseDTO addRole(RoleAddUpdateDTO dto);
    RoleAddUpdateResponseDTO updateRole(UUID roleId, RoleAddUpdateDTO dto);
    void deleteRole(UUID roleId);
    List<EmployeeSummaryDTO> getEmployeesByRole(UUID roleId);
    EmployeeSummaryDTO updateRoleByEmployeeId(UUID roleId, UUID employeeId);
}
