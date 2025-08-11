package com.example.auth.model.dto.task;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class TaskListRequestDTO {
    Set<UUID> employeeIDs;
}
