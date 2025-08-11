package com.example.auth.model;

import com.example.auth.constants.Constants;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = Constants.COMMENTS)
public class Comment extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "comment_id", nullable = false, updatable = false)
    private UUID commentId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    // ✅ Belongs to Task
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @JsonBackReference
    private Task task;

}
