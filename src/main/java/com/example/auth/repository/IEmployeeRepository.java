package com.example.auth.repository;

import com.example.auth.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;


public interface IEmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByEmail(String username);
}
