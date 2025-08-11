package com.example.auth.service;

import com.example.auth.model.dto.project.ProjectAddRequestDTO;
import com.example.auth.model.dto.project.ProjectResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IProjectService {
    Page<ProjectResponseDTO> getProjects(String query, Pageable pageable);
    ProjectResponseDTO createProject(ProjectAddRequestDTO dto);
    ProjectResponseDTO updateProject(UUID projectId, ProjectAddRequestDTO dto);
    ProjectResponseDTO getProjectById(UUID projectId);
}
