package com.example.auth.repository.predicate;

import com.example.auth.model.QTask;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Set;
import java.util.UUID;

public class TaskPredicate {
    private static final QTask task = QTask.task;

    public static BooleanExpression findByQuery(String query) {
        return task.taskName.containsIgnoreCase(query).or(task.taskDescription.containsIgnoreCase(query));
    }

    public static BooleanExpression findByProjectId(Long projectId){
        return task.project.projectId.eq(projectId);
    }

    public static BooleanExpression findByEmployeeId(Set<UUID> employeeIds){
        return task.assignedTo.id.in(employeeIds);
    }
}
