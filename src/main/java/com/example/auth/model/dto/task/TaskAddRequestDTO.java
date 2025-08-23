package com.example.auth.model.dto.task;

import com.example.auth.model.Comment;
import com.example.auth.model.Employee;
import com.example.auth.model.Project;
import com.example.auth.model.enums.Priority;
import com.example.auth.model.enums.TaskStatus;
import com.example.auth.model.enums.TaskType;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class TaskAddRequestDTO {

    @NotBlank
    @Size(max = 200, message = "Task name must be at most 200 characters")
    private String taskName;

    @Size(max = 2000, message = "Task details must be at most 2000 characters")
    private String taskDescription;

    @NotNull
    private Priority priority;

    @NotNull
    private TaskType type;

    private UUID assignedTo;

//    private Employee managedBy;
//    private Project project;

    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDateTime startDate;

    @FutureOrPresent(message = "End date cannot be in the past")
    private LocalDateTime endDate;

}
