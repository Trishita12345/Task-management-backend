package com.example.auth.controller;

import com.example.auth.model.Project;
import com.example.auth.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @GetMapping(path = "/page")
    public ResponseEntity<Page<Project>> getProjectsPage(
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

}
