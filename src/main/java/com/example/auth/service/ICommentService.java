package com.example.auth.service;

import com.example.auth.model.dto.comment.CommentRequestDTO;
import com.example.auth.model.dto.comment.CommentResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ICommentService {
    CommentResponseDTO addComment(UUID taskId, CommentRequestDTO commentRequestDTO);
    CommentResponseDTO editComment(UUID taskId, UUID commentId, CommentRequestDTO commentRequestDTO);
    void deleteComment(UUID taskId, UUID commentId);
    Page<CommentResponseDTO> getCommentsByTaskId(UUID taskId, Integer page, String direction);
}
