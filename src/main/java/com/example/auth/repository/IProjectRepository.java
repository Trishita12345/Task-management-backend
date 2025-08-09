package com.example.auth.repository;

import com.example.auth.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface IProjectRepository extends JpaRepository<Project, Long> , QuerydslPredicateExecutor<Project> {
    
}
