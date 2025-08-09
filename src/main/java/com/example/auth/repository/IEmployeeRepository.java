package com.example.auth.repository;

import com.example.auth.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;
import java.util.UUID;


public interface IEmployeeRepository extends JpaRepository<Employee, UUID>, QuerydslPredicateExecutor<Employee> {

    Optional<Employee> findByEmail(String username);
}
