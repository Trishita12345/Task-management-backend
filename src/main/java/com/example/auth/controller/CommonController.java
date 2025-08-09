package com.example.auth.controller;

import com.example.auth.model.dto.common.SelectOptionDTO;
import com.example.auth.model.enums.Priority;
import com.example.auth.model.enums.TaskStatus;
import com.example.auth.model.enums.TaskType;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.auth.model.mapper.EntityToSelectedOptionMapper.entityToSelectedOptionListMapper;

@RestController
@RequestMapping("/authenticated")
public class CommonController {

    @GetMapping(path = "/tasks/types")
    @Operation(summary = "Get Types of Tasks")
    public ResponseEntity<List<SelectOptionDTO<String>>> getTaskTypes(){
        return ResponseEntity.ok(entityToSelectedOptionListMapper(List.of(TaskType.values())));
    }

    @GetMapping(path = "/tasks/statuses")
    @Operation(summary = "Get Statuses of Tasks")
    public ResponseEntity<List<SelectOptionDTO<String>>> getTaskStatuses(){
        return ResponseEntity.ok(entityToSelectedOptionListMapper(List.of(TaskStatus.values())));
    }

    @GetMapping(path = "/tasks/priorities")
    @Operation(summary = "Get Priorities of Tasks")
    public ResponseEntity<List<SelectOptionDTO<String>>> getTaskPriorities(){
        return ResponseEntity.ok(entityToSelectedOptionListMapper(List.of(Priority.values())));
    }


}
