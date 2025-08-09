package com.example.auth.repository.predicate;

import com.example.auth.model.QComment;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.UUID;

public class CommentPredicate {
    private static final QComment comment = QComment.comment;

    public static BooleanExpression findByTaskId(UUID taskId){
        return comment.task.taskId.eq(taskId);
    }
}
