package com.example.auth.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
    private List<Map<String, String>> errors;
}

