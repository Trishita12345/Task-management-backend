package com.example.auth.controller;

import com.example.auth.dto.common.PageRequestDTO;
import com.example.auth.dto.project.ProjectAddRequestDTO;
import com.example.auth.dto.project.ProjectResponseDTO;
import com.example.auth.model.Employee;
import com.example.auth.model.Project;
import com.example.auth.service.IEmployeeService;
import com.example.auth.service.IProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping(path = "/authenticated/projects")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @PreAuthorize("hasAuthority('VIEW_PROJECTS')")
    @PostMapping(path="/page")
    public ResponseEntity<Page<ProjectResponseDTO>> getProjectsByEmpIdPage(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestBody PageRequestDTO pageRequestDTO
    ) {
        Sort sort = pageRequestDTO.getDirection().equalsIgnoreCase("desc") ?
                Sort.by(pageRequestDTO.getSortBy()).descending() :
                Sort.by(pageRequestDTO.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), sort);
        return ResponseEntity.ok(projectService.getProjects(query, pageable));
    }

    @PreAuthorize("hasAuthority('VIEW_PROJECTS')")
    @GetMapping(path="/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectByIdByEmpId(@Valid @PathVariable @NotNull @Positive Long projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @PreAuthorize("hasAuthority('ADD_PROJECTS')")
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectAddRequestDTO dto) {
        ProjectResponseDTO response = projectService.createProject(dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('EDIT_PROJECT')")
    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@Valid @PathVariable @NotNull @Positive Long projectId, @Valid @RequestBody ProjectAddRequestDTO dto) {
        ProjectResponseDTO response = projectService.updateProject(projectId, dto);
        return ResponseEntity.ok(response);
    }

    //TODO: DELETE_PROJECT
}
