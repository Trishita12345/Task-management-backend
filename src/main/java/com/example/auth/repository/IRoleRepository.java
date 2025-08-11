package com.example.auth.repository;

import com.example.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoleRepository extends JpaRepository<Role, UUID>, QuerydslPredicateExecutor<Role> {
    Optional<Role> findByName(String name);
    boolean existsByName(String role);
}
