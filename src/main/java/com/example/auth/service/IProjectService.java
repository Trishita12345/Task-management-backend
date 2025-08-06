package com.example.auth.service;

import com.example.auth.dto.project.ProjectAddRequestDTO;
import com.example.auth.dto.project.ProjectResponseDTO;
import com.example.auth.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IProjectService {
    Page<ProjectResponseDTO> getProjects(String query, Pageable pageable);
    ProjectResponseDTO createProject(ProjectAddRequestDTO dto);
    ProjectResponseDTO updateProject(Long projectId, ProjectAddRequestDTO dto);
    ProjectResponseDTO getProjectById(Long projectId);
}
