package com.example.auth.controller;

import com.example.auth.dto.project.ProjectAddRequestDTO;
import com.example.auth.dto.project.ProjectResponseDTO;
import com.example.auth.model.Employee;
import com.example.auth.model.Project;
import com.example.auth.service.IEmployeeService;
import com.example.auth.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/authenticated/projects")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @GetMapping(path = "/page")
    public ResponseEntity<Page<ProjectResponseDTO>> getProjectsPage(
            @RequestParam(required = false, defaultValue = "") String query,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "createdAt") String sortBy,   // <-- sorting column
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(projectService.getProjects(query, pageable));
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectAddRequestDTO dto) {
        ProjectResponseDTO response = projectService.createProject(dto);
        return ResponseEntity.ok(response);
    }

}
