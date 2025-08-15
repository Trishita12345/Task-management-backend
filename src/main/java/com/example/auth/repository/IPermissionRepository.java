package com.example.auth.repository;

import com.example.auth.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPermissionRepository extends JpaRepository<Permission, UUID> {
    boolean existsByName(String permissionName);
}
