package com.example.auth.controller;

import com.example.auth.model.dto.common.PageRequestDTO;
import com.example.auth.model.dto.common.SelectOptionDTO;
import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import com.example.auth.model.dto.role.RoleAddUpdateDTO;
import com.example.auth.model.dto.role.RoleAddUpdateResponseDTO;
import com.example.auth.model.dto.role.RoleResponseDTO;
import com.example.auth.service.IAdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('VIEW_ROLES')")
    @PostMapping(path="/get-roles/page")
    @Operation(summary = "Get all roles page")
    public ResponseEntity<Page<RoleResponseDTO>> getRolesPage(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestBody PageRequestDTO pageRequestDTO
    ) {
        if(pageRequestDTO.getSortBy().equals("createdAt")) {
            pageRequestDTO.setSortBy("name");
        };
        Sort sort = pageRequestDTO.getDirection().equalsIgnoreCase("desc") ?
                Sort.by(pageRequestDTO.getSortBy()).descending() :
                Sort.by(pageRequestDTO.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), sort);
        return ResponseEntity.ok(adminService.getRolesPage(query, pageable));
    }

    @GetMapping(path = "/get-employees")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('VIEW_EMPLOYEES')")
    @Operation(summary = "Get Employees by RoleId of application")
    public ResponseEntity<List<SelectOptionDTO<UUID>>> getEmployeesByRole(
            @RequestParam(required = false) UUID roleId){
        return ResponseEntity.ok(adminService.getEmployeesByRole(roleId));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('VIEW_EMPLOYEES')")
    @PostMapping(path="/get-employees/page")
    @Operation(summary = "Get all employees page")
    public ResponseEntity<Page<EmployeeSummaryDTO>> getEmployeesPage(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestBody PageRequestDTO pageRequestDTO
    ) {
        if(pageRequestDTO.getSortBy().equals("createdAt")) {
            pageRequestDTO.setSortBy("firstname");
        };
        Sort sort = pageRequestDTO.getDirection().equalsIgnoreCase("desc") ?
                Sort.by(pageRequestDTO.getSortBy()).descending() :
                Sort.by(pageRequestDTO.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), sort);
        return ResponseEntity.ok(adminService.getEmployeesPage(query, pageable));
    }

    @GetMapping(path = "/get-permissions")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('VIEW_ROLES')")
    @Operation(summary = "Get Permissions of application")
    public ResponseEntity<List<SelectOptionDTO<UUID>>> getPermissions(){
        return ResponseEntity.ok(adminService.getPermissions());
    }

    @PostMapping(path = "/add-role")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('ADD_ROLES')")
    @Operation(summary = "Add Role in application")
    public ResponseEntity<RoleAddUpdateResponseDTO> addRole(
            @Valid @RequestBody RoleAddUpdateDTO roleAddUpdateDTO){
        return ResponseEntity.ok(adminService.addRole(roleAddUpdateDTO));
    }
    @GetMapping(path = "/get-role/{roleId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('VIEW_ROLES')")
    @Operation(summary = "Get Role Details in application")
    public ResponseEntity<RoleAddUpdateResponseDTO> getRoleById(
            @Valid @PathVariable UUID roleId){
        return ResponseEntity.ok(adminService.getRoleById(roleId));
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
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('ASSIGN_ROLE_EMPLOYEES')")
    @Operation(summary = "Assign or Update Role of Employee")
    public ResponseEntity<EmployeeSummaryDTO> updateRoleByEmployeeId(
            @Valid @PathVariable @NotNull UUID roleId,
            @Valid @PathVariable @NotNull UUID employeeId){
        return ResponseEntity.ok(adminService.updateRoleByEmployeeId(roleId, employeeId));
    }

//    @DeleteMapping(path = "/delete-role/{roleId}")
//    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('DELETE_ROLES')")
//    @Operation(summary = "Delete Role of application")
//    public ResponseEntity<RoleAddUpdateResponseDTO> deleteRole(
//            @Valid @PathVariable @NotNull UUID roleId){
//        return ResponseEntity.noContent().build();
//    }
}
