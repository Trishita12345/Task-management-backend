package com.example.auth.controller;

import com.example.auth.model.dto.common.SelectOptionDTO;
import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import com.example.auth.model.dto.role.RoleAddUpdateDTO;
import com.example.auth.model.dto.role.RoleAddUpdateResponseDTO;
import com.example.auth.service.IAdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/authenticated/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @GetMapping(path = "/get-roles")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('VIEW_ROLES')")
    @Operation(summary = "Get Roles of application")
    public ResponseEntity<List<SelectOptionDTO<UUID>>> getRoles(){
        return ResponseEntity.ok(adminService.getRoles());
    }

    @GetMapping(path = "/get-employees")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('VIEW_ROLES')")
    @Operation(summary = "Get Employees by RoleId of application")
    public ResponseEntity<List<EmployeeSummaryDTO>> getEmployeesByRole(
            @RequestParam(required = false) UUID roleId){
        return ResponseEntity.ok(adminService.getEmployeesByRole(roleId));
    }

    @GetMapping(path = "/get-permissions")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('VIEW_ROLES')")
    @Operation(summary = "Get Permissions of application")
    public ResponseEntity<List<SelectOptionDTO<Long>>> getPermissions(){
        return ResponseEntity.ok(adminService.getPermissions());
    }

    @PostMapping(path = "/add-role")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('ADD_ROLES')")
    @Operation(summary = "Add Role in application")
    public ResponseEntity<RoleAddUpdateResponseDTO> addRole(
            @Valid @RequestBody RoleAddUpdateDTO roleAddUpdateDTO){
        return ResponseEntity.ok(adminService.addRole(roleAddUpdateDTO));
    }

    @PutMapping(path = "/update-role/{roleId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('EDIT_ROLES')")
    @Operation(summary = "Update Role of application")
    public ResponseEntity<RoleAddUpdateResponseDTO> updateRole(
            @Valid @PathVariable UUID roleId,
            @Valid @RequestBody RoleAddUpdateDTO roleAddUpdateDTO){
        return ResponseEntity.ok(adminService.updateRole(roleId, roleAddUpdateDTO));
    }

    @PatchMapping(path = "/update-role/{employeeId}/{roleId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('ASSIGN_ROLE')")
    @Operation(summary = "Assign or Update Role of Employee")
    public ResponseEntity<EmployeeSummaryDTO> updateRoleByEmployeeId(
            @Valid @PathVariable @NotNull UUID roleId,
            @Valid @PathVariable @NotNull UUID employeeId){
        return ResponseEntity.ok(adminService.updateRoleByEmployeeId(roleId, employeeId));
    }

    @DeleteMapping(path = "/delete-role/{roleId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('DELETE_ROLES')")
    @Operation(summary = "Delete Role of application")
    public ResponseEntity<RoleAddUpdateResponseDTO> deleteRole(
            @Valid @PathVariable @NotNull UUID roleId){
        return ResponseEntity.noContent().build();
    }
}
