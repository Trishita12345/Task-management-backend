package com.example.auth.service;

import com.example.auth.constants.Constants;
import com.example.auth.dto.project.ProjectAddRequestDTO;
import com.example.auth.dto.project.ProjectResponseDTO;
import com.example.auth.model.Employee;
import com.example.auth.model.Project;
import com.example.auth.model.mapper.ProjectResponseMapper;
import com.example.auth.repository.IProjectRepository;
import com.example.auth.repository.predicate.ProjectPredicate;
import com.example.auth.util.SecurityUtil;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@Service
public class ProjectService implements IProjectService{

    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ProjectResponseMapper projectResponseMapper;

    @Override
    public Page<ProjectResponseDTO> getProjects(String query, Pageable pageable) {
        Page<Project> projects;
        Employee currentUser = SecurityUtil.getCurrentEmployee();
        if (query == null || query.trim().isEmpty()) {
            // No search query → return all
            projects = projectRepository.findAll(ProjectPredicate.findByEmployeeId(currentUser.getId()), pageable);
        } else {
            // Search by name or details
            projects = projectRepository.findAll(
                     ProjectPredicate.findByQuery(query, query)
                    .and(ProjectPredicate.findByEmployeeId(currentUser.getId())), pageable);
        }
        return projects.map(projectResponseMapper::toResponse);
    }

    @Override
    @Transactional
    public ProjectResponseDTO createProject(ProjectAddRequestDTO dto) {
        Employee currentUser = SecurityUtil.getCurrentEmployee();
        Project project = new Project();
        project.setName(dto.getName());
        project.setDetails(dto.getDetails());
        project.setEmployees(Set.of(currentUser));
        Project response_project = projectRepository.save(project);
        // ✅ Convert to response DTO
        return projectResponseMapper.toResponse(response_project);
    }

    @Override
    public ProjectResponseDTO updateProject(Long projectId, ProjectAddRequestDTO dto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NoSuchElementException(Constants.PROJECT_NOT_FOUND));
        project.setName(dto.getName());
        project.setDetails(dto.getDetails());
        Project response_project = projectRepository.save(project);
        // ✅ Convert to response DTO
        return projectResponseMapper.toResponse(response_project);
    }

    @Override
    public ProjectResponseDTO getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NoSuchElementException(Constants.PROJECT_NOT_FOUND));
        return projectResponseMapper.toResponse(project);
    }
}
