package com.example.auth.model.dto.task;

import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import com.example.auth.model.enums.Priority;
import com.example.auth.model.enums.TaskStatus;
import com.example.auth.model.enums.TaskType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TaskResponseDTO {
    private UUID taskId;
    private String taskName;
    private Priority priority;
    private TaskStatus status;
    private TaskType type;
    private EmployeeSummaryDTO assignedTo;
}
