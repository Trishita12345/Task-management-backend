package com.example.auth.service;

import com.example.auth.dto.project.ProjectAddRequestDTO;
import com.example.auth.dto.project.ProjectResponseDTO;
import com.example.auth.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProjectService {
    Page<ProjectResponseDTO> getProjects(String query, Pageable pageable);
    ProjectResponseDTO createProject(ProjectAddRequestDTO dto);
}
