package com.example.auth.repository;

import com.example.auth.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findByNameContainingIgnoreCaseOrDetailsContainingIgnoreCase(
            String name,
            String details,
            Pageable pageable
    );
}
