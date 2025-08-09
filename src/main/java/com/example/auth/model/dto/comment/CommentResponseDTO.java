package com.example.auth.model.dto.comment;

import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDTO {
    private Long commentId;
    private String content;
    private EmployeeSummaryDTO createdBy;
    private EmployeeSummaryDTO updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
