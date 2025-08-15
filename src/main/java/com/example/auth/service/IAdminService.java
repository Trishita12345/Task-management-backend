package com.example.auth.service;

import com.example.auth.model.dto.common.SelectOptionDTO;
import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import com.example.auth.model.dto.role.RoleAddUpdateDTO;
import com.example.auth.model.dto.role.RoleAddUpdateResponseDTO;
import com.example.auth.model.dto.role.RoleResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IAdminService {
    List<SelectOptionDTO<UUID>> getRoles();
    Page<RoleResponseDTO> getRolesPage(String query, Pageable pageable);
    Page<EmployeeSummaryDTO> getEmployeesPage(String query, Pageable pageable);
    List<SelectOptionDTO<UUID>> getPermissions();
    RoleAddUpdateResponseDTO addRole(RoleAddUpdateDTO dto);
    RoleAddUpdateResponseDTO updateRole(UUID roleId, RoleAddUpdateDTO dto);
    void deleteRole(UUID roleId);
    List<EmployeeSummaryDTO> getEmployeesByRole(UUID roleId);
    EmployeeSummaryDTO updateRoleByEmployeeId(UUID roleId, UUID employeeId);
    RoleAddUpdateResponseDTO getRoleById(@Valid UUID roleId);
}
