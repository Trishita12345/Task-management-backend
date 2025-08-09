package com.example.auth.model.mapper;

import com.example.auth.model.dto.comment.CommentResponseDTO;
import com.example.auth.model.Comment;

import static com.example.auth.model.mapper.EmployeeDetailsMapper.toEmployeeSummary;

public class CommentResponseMapper {
    public static CommentResponseDTO toCommentDetails(Comment comment) {
        if (comment == null) return null;
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setCommentId(comment.getCommentId());
        dto.setContent(comment.getContent());
        dto.setCreatedBy(toEmployeeSummary(comment.getCreatedBy()));
        dto.setUpdatedBy(toEmployeeSummary(comment.getUpdatedBy()));
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }
}
