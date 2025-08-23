package com.example.auth.service.impl;

import com.example.auth.model.Employee;
import com.example.auth.model.Project;
import com.example.auth.model.Task;
import com.example.auth.model.dto.task.*;
import com.example.auth.model.enums.Priority;
import com.example.auth.model.enums.TaskStatus;
import com.example.auth.model.enums.TaskType;
import com.example.auth.model.mapper.TaskDetailResponseMapper;
import com.example.auth.model.mapper.TaskResponseMapper;
import com.example.auth.repository.IEmployeeRepository;
import com.example.auth.repository.IProjectRepository;
import com.example.auth.repository.ITaskRepository;
import com.example.auth.repository.predicate.TaskPredicate;
import com.example.auth.service.ITaskService;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public List<TaskResponseDTO> getTasks(UUID projectId, String query, TaskListRequestDTO taskListRequestDTO) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found"));
        List<Task> tasklist = new ArrayList<>();
        Iterable<Task> tasks;
        BooleanExpression ex = TaskPredicate.findByProjectId(projectId);
        if (query != null) {
            ex = ex.and(TaskPredicate.findByQuery(query));
        }
        if(taskListRequestDTO.getEmployeeIDs()!=null && !taskListRequestDTO.getEmployeeIDs().isEmpty()){
            ex = ex.and(TaskPredicate.findByEmployeeId(taskListRequestDTO.getEmployeeIDs()));
        }
        tasks = taskRepository.findAll(ex);
        for (Task task : tasks) {
            tasklist.add(task);
        }
        return tasklist.stream().map(TaskResponseMapper::toTaskResponse).toList();
    }

    @Override
    public TaskDetailResponseDTO getTaskById(UUID projectId, UUID taskId) {
        Task task = taskExistInProject(projectId, taskId);
        return TaskDetailResponseMapper.toTaskDetailResponse(task);
    }

    @Transactional
    @Override
    public TaskResponseDTO addTask(UUID projectId, TaskAddRequestDTO taskAddRequestDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found"));

        Employee employee = null;
        if(taskAddRequestDTO.getAssignedTo() != null){
            employee = employeeRepository.findById(taskAddRequestDTO.getAssignedTo())
                    .orElseThrow(() -> new NoSuchElementException("Assignee not found"));
        }
        if (taskAddRequestDTO.getStartDate() != null && taskAddRequestDTO.getEndDate() != null && taskAddRequestDTO.getStartDate().isAfter(taskAddRequestDTO.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        Task newTask = taskAddDTOToEntity(taskAddRequestDTO, employee, project);
        Task savedTask = taskRepository.save(newTask);
        return TaskResponseMapper.toTaskResponse(savedTask);
    }
    private static Task taskAddDTOToEntity(TaskAddRequestDTO taskAddRequestDTO, Employee employee, Project project) {
        Task newTask = new Task();
        newTask.setTaskName(taskAddRequestDTO.getTaskName());
        newTask.setTaskDescription(taskAddRequestDTO.getTaskDescription());
        newTask.setPriority(taskAddRequestDTO.getPriority());
        newTask.setType(taskAddRequestDTO.getType());
        newTask.setAssignedTo(employee);
        newTask.setProject(project);
        newTask.setStartDate(taskAddRequestDTO.getStartDate());
        newTask.setEndDate(taskAddRequestDTO.getEndDate());
        return newTask;
    }

    @Transactional
    @Override
    public TaskDetailResponseDTO editTask(UUID projectId, UUID taskId, TaskEditRequestDTO taskEditRequestDTO) {
        Task task = taskExistInProject(projectId, taskId);
        String fieldName = taskEditRequestDTO.getKey();
        Object value = taskEditRequestDTO.getValue();

        switch (fieldName) {
            case "taskId": // Immutable field
                throw new IllegalArgumentException("taskId cannot be updated");

            case "assignedTo": // Foreign key - User
                UUID userId = UUID.fromString(value.toString());
                Employee user = employeeRepository.findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
                task.setAssignedTo(user);
                break;

            case "priority": // Enum
                task.setPriority(Priority.valueOf(value.toString().toUpperCase()));
                break;

            case "status": // Enum
                task.setStatus(TaskStatus.valueOf(value.toString().toUpperCase()));
                break;

            case "type": // Enum
                task.setType(TaskType.valueOf(value.toString().toUpperCase()));
                break;

            default: // Regular field via reflection
                try {
                    Field field = Task.class.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    if (!field.getType().isAssignableFrom(value.getClass())) {
                        throw new IllegalArgumentException("Value type " + value.getClass().getName() +
                                " is not assignable to field " + fieldName);
                    }

                    field.set(task, value);
                } catch (NoSuchFieldException e) {
                    throw new IllegalArgumentException("Field '" + fieldName + "' not found");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
        }
        Task updatedTask = taskRepository.save(task);
        return TaskDetailResponseMapper.toTaskDetailResponse(updatedTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID projectId, UUID taskId) {
        Task task = taskExistInProject(projectId, taskId);
        taskRepository.delete(task);
    }

    private Task taskExistInProject(UUID projectId, UUID taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        if(!Objects.equals(task.getProject().getProjectId(), projectId)){
            throw new NoSuchElementException("Task not found in the project");
        }
        return task;
    }
    
}


