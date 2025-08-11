package com.example.auth.model.dto.comment;

import com.example.auth.model.dto.project.EmployeeSummaryDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CommentResponseDTO {
    private UUID commentId;
    private String content;
    private EmployeeSummaryDTO createdBy;
    private EmployeeSummaryDTO updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
