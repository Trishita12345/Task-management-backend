package com.example.auth.service;

import com.example.auth.model.dto.task.*;

import java.util.List;
import java.util.UUID;

public interface ITaskService {
    List<TaskResponseDTO> getTasks(UUID projectId, String query, TaskListRequestDTO taskListRequestDTO);
    TaskDetailResponseDTO getTaskById(UUID projectId, UUID taskId);
    TaskResponseDTO addTask(UUID projectId, TaskAddRequestDTO taskAddRequestDTO);
    TaskDetailResponseDTO editTask(UUID projectId, UUID taskId, TaskEditRequestDTO taskEditRequestDTO);
    void deleteTask(UUID projectId, UUID taskId);
}
