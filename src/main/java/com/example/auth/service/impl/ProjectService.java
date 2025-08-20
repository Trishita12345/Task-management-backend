package com.example.auth.service.impl;

import com.example.auth.constants.Constants;
import com.example.auth.model.Task;
import com.example.auth.model.dto.project.ProjectAddRequestDTO;
import com.example.auth.model.dto.project.ProjectResponseDTO;
import com.example.auth.model.Employee;
import com.example.auth.model.Project;
import com.example.auth.model.mapper.ProjectResponseMapper;
import com.example.auth.repository.IEmployeeRepository;
import com.example.auth.repository.IProjectRepository;
import com.example.auth.repository.ITaskRepository;
import com.example.auth.repository.predicate.ProjectPredicate;
import com.example.auth.service.IEmployeeService;
import com.example.auth.service.IProjectService;
import com.example.auth.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IEmployeeRepository employeeRepository;
    @Autowired
    private ITaskRepository taskRepository;

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
                     ProjectPredicate.findByQuery(query)
                    .and(ProjectPredicate.findByEmployeeId(currentUser.getId())), pageable);
        }
        return projects.map(ProjectResponseMapper::toResponse);
    }

    @Override
    @Transactional
    public ProjectResponseDTO createProject(ProjectAddRequestDTO dto) {
        Employee currentUser = SecurityUtil.getCurrentEmployee();
        Employee manager = employeeRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new NoSuchElementException("Manager not found"));
        Project project = new Project();
        project.setName(dto.getName());
        project.setDetails(dto.getDetails());
        project.setManager(manager);
        // Add Project Owner and Manager by Default
        Set<Employee> employees = new HashSet<>();
        employees.add(currentUser);
        employees.add(manager);
        // Add employees assigned
        List<Employee> employeeOnly = employeeRepository.findAllById(dto.getEmployeeIds());
        if(!employeeOnly.isEmpty()) {
            employees.addAll(employeeOnly);
        }
        project.setEmployees(employees);
        Project response_project = projectRepository.save(project);
        // ✅ Convert to response DTO
        return ProjectResponseMapper.toResponse(response_project);
    }

    @Transactional
    @Override
    public ProjectResponseDTO updateProject(UUID projectId, ProjectAddRequestDTO dto) {
        Employee currentUser = SecurityUtil.getCurrentEmployee();
        Employee manager = employeeRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new NoSuchElementException("Manager not found"));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NoSuchElementException(Constants.PROJECT_NOT_FOUND));
        project.setName(dto.getName());
        project.setDetails(dto.getDetails());
        project.setManager(manager);
        // Add employees assigned
        Set<Employee> employees = new HashSet<>(employeeRepository.findAllById(dto.getEmployeeIds()));
        employees.add(currentUser); employees.add(manager);
        // Need to remove employees assigned tasks which are in same project
        Set<Employee> employeesUnassigned = project.getEmployees()
                .stream().filter(employee -> !employees.contains(employee))
                .collect(Collectors.toSet());
        // get tasks to update
        List<Task> tasksUpdated = project.getTasks().stream().filter(task ->
                employeesUnassigned.contains(task.getAssignedTo())
        ).peek(task -> task.setAssignedTo(null)).toList();
        // save updatedTasks
        taskRepository.saveAll(tasksUpdated);
        // set employees in Project
        project.setEmployees(employees);
        // save Project
        Project response_project = projectRepository.save(project);
        // ✅ Convert to response DTO
        return ProjectResponseMapper.toResponse(response_project);
    }


    @Override
    public ProjectResponseDTO getProjectById(UUID projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NoSuchElementException(Constants.PROJECT_NOT_FOUND));
        return ProjectResponseMapper.toResponse(project);
    }
}
