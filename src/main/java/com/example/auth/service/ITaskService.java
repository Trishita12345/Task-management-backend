package com.example.auth.service;

import com.example.auth.model.dto.task.*;

import java.util.List;
import java.util.UUID;

public interface ITaskService {
    List<TaskResponseDTO> getTasks(Long projectId, String query, TaskListRequestDTO taskListRequestDTO);
    TaskDetailResponseDTO getTaskById(Long projectId, UUID taskId);
    TaskResponseDTO addTask(Long projectId, TaskAddRequestDTO taskAddRequestDTO);
    TaskDetailResponseDTO editTask(Long projectId, UUID taskId, TaskEditRequestDTO taskEditRequestDTO);
    void deleteTask(Long projectId, UUID taskId);
}
