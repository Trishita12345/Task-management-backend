package com.example.auth.model.dto.task;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class TaskListRequestDTO {
    Set<UUID> employeeIDs;
}
