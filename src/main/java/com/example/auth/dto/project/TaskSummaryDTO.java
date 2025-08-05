package com.example.auth.dto.project;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskSummaryDTO {

        private UUID taskId;
        private String taskName;
        private String taskDescription;
        private String priority;
        private String status;

        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
}
