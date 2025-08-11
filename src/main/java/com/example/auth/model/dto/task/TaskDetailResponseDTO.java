package com.example.auth.model.dto.task;

import com.example.auth.model.dto.comment.CommentResponseDTO;
import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import com.example.auth.model.enums.Priority;
import com.example.auth.model.enums.TaskStatus;
import com.example.auth.model.enums.TaskType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TaskDetailResponseDTO {
    private UUID taskId;
    private String taskName;
    private String taskDescription;
    private Priority priority;
    private TaskStatus status;
    private TaskType type;
    private EmployeeSummaryDTO assignedTo;
    private EmployeeSummaryDTO managedBy;
//    private Project project;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private EmployeeSummaryDTO createdBy;
    private EmployeeSummaryDTO updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
