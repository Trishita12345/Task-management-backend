package com.example.auth.service;

import com.example.auth.model.Project;
import com.example.auth.repository.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService implements IProjectService{

    @Autowired
    private IProjectRepository projectRepository;

    @Override
    public Page<Project> getProjects(String query, Pageable pageable) {
        if (query == null || query.trim().isEmpty()) {
            // ✅ No search query → return all
            return projectRepository.findAll(pageable);
        } else {
            // ✅ Search by name or details
            return projectRepository.findByNameContainingIgnoreCaseOrDetailsContainingIgnoreCase(query, query, pageable);
        }
    }
}
