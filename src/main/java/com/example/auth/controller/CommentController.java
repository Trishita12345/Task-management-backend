package com.example.auth.controller;

import com.example.auth.model.dto.comment.CommentRequestDTO;
import com.example.auth.model.dto.comment.CommentResponseDTO;
import com.example.auth.service.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/authenticated/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @GetMapping("/page/{taskId}/{page}")
    @PreAuthorize("hasAuthority('VIEW_TASKS')")
    @Operation(summary = "Get Comments by taskId paginated")
    public ResponseEntity<Page<CommentResponseDTO>> getAllCommentsByTaskId(
            @Valid @PathVariable @NotNull UUID taskId,
            @Valid @PathVariable(required = false) Integer page
    ){
        return ResponseEntity.ok(commentService.getCommentsByTaskId(taskId, page));
    }

    @PostMapping(path = "/{taskId}")
    @PreAuthorize("hasAuthority('EDIT_TASKS')")
    @Operation(summary = "Add Comments")
    public ResponseEntity<CommentResponseDTO> addComment(
            @Valid @PathVariable @NotNull UUID taskId,
            @Valid @RequestBody CommentRequestDTO commentRequestDTO
    ){
        return ResponseEntity.ok(commentService.addComment(taskId, commentRequestDTO));
    }

    @PutMapping(path = "/{taskId}/{commentId}")
    @PreAuthorize("hasAuthority('EDIT_TASKS')")
    @Operation(summary = "Edit Comments")
    public ResponseEntity<CommentResponseDTO> editComment(
            @Valid @PathVariable @NotNull UUID taskId,
            @Valid @PathVariable @NotNull UUID commentId,
            @Valid @RequestBody CommentRequestDTO commentRequestDTO
    ){
        return ResponseEntity.ok(commentService.editComment(taskId, commentId, commentRequestDTO));
    }

    @DeleteMapping(path = "/{taskId}/{commentId}")
    @PreAuthorize("hasAuthority('EDIT_TASKS')")
    @Operation(summary = "Delete Comments")
    public ResponseEntity<Void> deleteComment(
            @Valid @PathVariable @NotNull UUID taskId,
            @Valid @PathVariable @NotNull UUID commentId
    ){
        commentService.deleteComment(taskId, commentId);
        return ResponseEntity.noContent().build();
    }

}
