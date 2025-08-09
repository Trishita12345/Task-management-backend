package com.example.auth.model.mapper;

import com.example.auth.model.dto.task.TaskResponseDTO;
import com.example.auth.model.Task;

import static com.example.auth.model.mapper.EmployeeDetailsMapper.toEmployeeSummary;

public class TaskResponseMapper {
    public static TaskResponseDTO toTaskResponse(Task task) {
        if (task == null) return null;

        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTaskName(task.getTaskName());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());
        dto.setType(task.getType());
        dto.setAssignedTo(toEmployeeSummary(task.getAssignedTo()));
        return dto;
    }
}
