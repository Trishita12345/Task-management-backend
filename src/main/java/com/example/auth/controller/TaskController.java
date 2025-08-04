package com.example.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(path = "/task")
public class TaskController {

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_TASKS')")
    public ResponseEntity<String> getTasks() {
        log.info("getTasks");
        return ResponseEntity.ok("getTasks");
    }

    @GetMapping(path = "/add")
    @PreAuthorize("hasAuthority('ADD_TASKS')")
    public ResponseEntity<String> addTasks() {
        log.info("addTasks");
        return ResponseEntity.ok("addTasks");
    }
}
