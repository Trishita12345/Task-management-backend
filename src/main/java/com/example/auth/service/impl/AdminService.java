package com.example.auth.service.impl;

import com.example.auth.model.Employee;
import com.example.auth.model.Role;
import com.example.auth.model.dto.common.SelectOptionDTO;
import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import com.example.auth.model.dto.role.RoleAddUpdateDTO;
import com.example.auth.model.dto.role.RoleAddUpdateResponseDTO;
import com.example.auth.model.dto.role.RoleResponseDTO;
import com.example.auth.model.mapper.EmployeeDetailsMapper;
import com.example.auth.model.mapper.RoleAddUpdateDtoMapper;
import com.example.auth.repository.IEmployeeRepository;
import com.example.auth.repository.IPermissionRepository;
import com.example.auth.repository.IRoleRepository;
import com.example.auth.repository.predicate.EmployeePredicate;
import com.example.auth.repository.predicate.RolePredicate;
import com.example.auth.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.auth.model.mapper.EntityToSelectedOptionMapper.entityToSelectedOptionListMapper;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IPermissionRepository permissionRepository;
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public List<SelectOptionDTO<UUID>> getRoles() {
        return entityToSelectedOptionListMapper(roleRepository.findAll());
    }

    @Override
    public Page<RoleResponseDTO> getRolesPage(String query, Pageable pageable) {
        Page<Role> roles;
        if (query == null || query.trim().isEmpty()) {
            // No search query → return all
            roles = roleRepository.findAll(pageable);
        } else {
            // Search by name or details
            roles = roleRepository.findAll(RolePredicate.findByQuery(query), pageable);
        }
        return roles.map(role -> RoleResponseDTO.builder()
                .roleId(role.getId())
                .name(role.getName()).build());
    }

    @Override
    public Page<EmployeeSummaryDTO> getEmployeesPage(String query, Pageable pageable) {
        Page<Employee> employees;
        if (query == null || query.trim().isEmpty()) {
            // No search query → return all
            employees = employeeRepository.findAll(pageable);
        } else {
            // Search by name or details
            employees = employeeRepository.findAll(EmployeePredicate.findByQuery(query), pageable);
        }
        return employees.map(EmployeeDetailsMapper::toEmployeeSummary);
    }

    @Override
    public List<SelectOptionDTO<UUID>> getPermissions() {
        return entityToSelectedOptionListMapper(permissionRepository.findAll());
    }

    @Transactional
    @Override
    public RoleAddUpdateResponseDTO addRole(RoleAddUpdateDTO dto) {
        Role role = roleRepository.findByName(dto.getName())
                .orElseThrow(() -> new RuntimeException("Role already exist."));
        role = new Role();
        role.setName(dto.getName());
        role.getPermissions().addAll(permissionRepository.findAllById(dto.getPermissions()));
        Role newRole = roleRepository.save(role);
        return RoleAddUpdateDtoMapper.toRoleAddUpdateResponseDto(newRole);
    }

    @Transactional
    @Override
    public RoleAddUpdateResponseDTO updateRole(UUID roleId, RoleAddUpdateDTO dto) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchElementException("Role not found"));
        role.setName(dto.getName());
        role.setPermissions(new HashSet<>(permissionRepository.findAllById(dto.getPermissions())));
        Role newRole = roleRepository.save(role);
        return RoleAddUpdateDtoMapper.toRoleAddUpdateResponseDto(newRole);
    }

    @Transactional
    @Override
    public void deleteRole(UUID roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchElementException("Role not found"));
        roleRepository.delete(role);
    }

    @Override
    public List<EmployeeSummaryDTO> getEmployeesByRole(UUID roleId) {
        List<Employee> employeeList = new ArrayList<>();
        if(roleId != null) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new NoSuchElementException("Role not found"));
            employeeRepository.findAll(EmployeePredicate
                    .findEmployeeByRoleId(roleId)).forEach(employeeList::add);
        } else {
            employeeList.addAll(employeeRepository.findAll());
        }
        return employeeList.stream().map(EmployeeDetailsMapper::toEmployeeSummary).toList();
    }

    @Transactional
    @Override
    public EmployeeSummaryDTO updateRoleByEmployeeId(UUID roleId, UUID employeeId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchElementException("Role not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        employee.setRole(role);
        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeDetailsMapper.toEmployeeSummary(updatedEmployee);
    }

    @Override
    public RoleAddUpdateResponseDTO getRoleById(UUID roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchElementException("Role not found"));
        return RoleAddUpdateDtoMapper.toRoleAddUpdateResponseDto(role);
    }
}
