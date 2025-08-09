package com.example.auth.repository;

import com.example.auth.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPermissionRepository extends JpaRepository<Permission, Long> {
    boolean existsByName(String permissionName);
}
