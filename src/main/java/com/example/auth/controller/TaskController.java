package com.example.auth.controller;

import com.example.auth.model.dto.task.*;
import com.example.auth.service.ITaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path = "/authenticated/tasks")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @PostMapping(path = "/{projectId}/list")
    @PreAuthorize("hasAuthority('VIEW_TASKS')")
    @Operation(summary = "Get Tasks by ProjectId")
    public ResponseEntity<List<TaskResponseDTO>> getTasks(
            @Valid @PathVariable @NotNull @Positive UUID projectId,
            @Valid @RequestParam(required = false, defaultValue = "") String query,
            @Valid @RequestBody TaskListRequestDTO taskListRequestDTO) {
        return ResponseEntity.ok(taskService.getTasks(projectId, query, taskListRequestDTO));
    }

    @GetMapping(path = "/{projectId}/{taskId}")
    @PreAuthorize("hasAuthority('VIEW_TASKS')")
    @Operation(summary = "Get Tasks by task id")
    public ResponseEntity<TaskDetailResponseDTO> getTaskById(
            @Valid @PathVariable @NotNull @Positive UUID projectId,
            @Valid @PathVariable @NotNull UUID taskId) {
        return ResponseEntity.ok(taskService.getTaskById(projectId, taskId));
    }

    @PostMapping(path = "/{projectId}")
    @PreAuthorize("hasAuthority('ADD_TASKS')")
    @Operation(summary = "Add Tasks by ProjectId")
    public ResponseEntity<TaskResponseDTO> addTasks(
            @Valid @PathVariable @NotNull @Positive UUID projectId,
            @Valid @RequestBody TaskAddRequestDTO taskAddRequestDTO) {
        return ResponseEntity.ok(taskService.addTask(projectId, taskAddRequestDTO));
    }

    @PatchMapping(path = "/{projectId}/{taskId}")
    @PreAuthorize("hasAuthority('EDIT_TASKS')")
    @Operation(summary = "Edit Tasks by task id")
    public ResponseEntity<TaskDetailResponseDTO> editTasks(
            @Valid @PathVariable @NotNull @Positive UUID projectId,
            @Valid @PathVariable @NotNull UUID taskId,
            @Valid @RequestBody TaskEditRequestDTO taskEditRequestDTO) {
        return ResponseEntity.ok(taskService.editTask(projectId, taskId, taskEditRequestDTO));
    }

    //TODO
    @DeleteMapping(path = "/{projectId}/{taskId}")
    @PreAuthorize("hasAuthority('DELETE_TASKS')")
    @Operation(summary = "Delete Tasks by task id")
    public ResponseEntity<Void> deleteTasks(
            @PathVariable("projectId") @NotNull @Positive UUID projectId,
            @PathVariable("taskId") @NotNull UUID taskId) {
        taskService.deleteTask(projectId, taskId);
        return ResponseEntity.noContent().build();
    }
}
