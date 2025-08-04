package com.example.auth.service;

import com.example.auth.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProjectService {
    Page<Project> getProjects(String query, Pageable pageable);
}
