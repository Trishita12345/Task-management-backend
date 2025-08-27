package com.example.auth.service.impl;

import com.example.auth.model.Comment;
import com.example.auth.model.Task;
import com.example.auth.model.dto.comment.CommentRequestDTO;
import com.example.auth.model.dto.comment.CommentResponseDTO;
import com.example.auth.model.mapper.CommentResponseMapper;
import com.example.auth.repository.ICommentRepository;
import com.example.auth.repository.ITaskRepository;
import com.example.auth.repository.predicate.CommentPredicate;
import com.example.auth.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private ITaskRepository taskRepository;

    @Transactional
    @Override
    public CommentResponseDTO addComment(UUID taskId, CommentRequestDTO commentRequestDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        Comment comment = new Comment();
        comment.setContent(commentRequestDTO.getContent());
        comment.setTask(task);
        Comment newComment = commentRepository.save(comment);
        return CommentResponseMapper.toCommentDetails(newComment);
    }

    @Transactional
    @Override
    public CommentResponseDTO editComment(UUID taskId, UUID commentId, CommentRequestDTO commentRequestDTO) {
        Comment comment = commentExistInTask(taskId, commentId);
        comment.setContent(commentRequestDTO.getContent());
        Comment newComment = commentRepository.save(comment);
        return CommentResponseMapper.toCommentDetails(newComment);
    }

    @Transactional
    @Override
    public void deleteComment(UUID taskId, UUID commentId) {
        Comment comment = commentExistInTask(taskId, commentId);
        commentRepository.delete(comment);
    }

    @Override
    public Page<CommentResponseDTO> getCommentsByTaskId(UUID taskId, Integer page, String direction) {
        Sort sort = Sort.by("createdAt");
        if(direction.equals("desc")){
            sort.descending();
        } else {
            sort.ascending();
        }
        Pageable pageable = PageRequest.of(page, 5, sort);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        Page<Comment> commentPage = commentRepository.findAll(CommentPredicate.findByTaskId(taskId), pageable);
        return commentPage.map(CommentResponseMapper::toCommentDetails);
    }

    private Comment commentExistInTask(UUID taskId, UUID commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));
        if(!Objects.equals(comment.getTask().getTaskId(), taskId)){
            throw new NoSuchElementException("Comment not found in the task");
        }
        return comment;
    }
}
