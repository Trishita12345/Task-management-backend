package com.example.auth.model.mapper;

import com.example.auth.model.dto.task.TaskDetailResponseDTO;
import com.example.auth.model.Task;

import java.util.ArrayList;

import static com.example.auth.model.mapper.EmployeeDetailsMapper.toEmployeeSummary;

public class TaskDetailResponseMapper {

    public static TaskDetailResponseDTO toTaskDetailResponse(Task task) {
        if (task == null) return null;
//        private Set<CommentResponseDTO> comments;
        TaskDetailResponseDTO dto = new TaskDetailResponseDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTaskName(task.getTaskName());
        dto.setTaskDescription(task.getTaskDescription());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());
        dto.setType(task.getType());
        dto.setAssignedTo(toEmployeeSummary(task.getAssignedTo()));
        dto.setManagedBy(toEmployeeSummary(task.getManagedBy()));
        dto.setStartDate(task.getStartDate());
        dto.setEndDate(task.getEndDate());
        dto.setCreatedBy(toEmployeeSummary(task.getCreatedBy()));
        dto.setUpdatedBy(toEmployeeSummary(task.getUpdatedBy()));
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        return dto;
    }
}
