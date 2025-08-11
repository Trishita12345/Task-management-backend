package com.example.auth.repository;

import com.example.auth.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface IProjectRepository extends JpaRepository<Project, UUID> , QuerydslPredicateExecutor<Project> {
    
}
