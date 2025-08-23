package com.example.auth.repository.predicate;

import com.example.auth.model.QTask;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskPredicate {
    private static final QTask task = QTask.task;

    public static BooleanExpression findByQuery(String query) {
        return task.taskName.containsIgnoreCase(query).or(task.taskDescription.containsIgnoreCase(query));
    }

    public static BooleanExpression findByProjectId(UUID projectId){
        return task.project.projectId.eq(projectId);
    }

    public static BooleanExpression findByEmployeeId(Set<UUID> employeeIds){
        if(employeeIds.contains(null)){
            Set<UUID> employeeIdsWithoutNull = employeeIds.stream().filter(Objects::nonNull).collect(Collectors.toSet());
            return task.assignedTo.id.in(employeeIdsWithoutNull).or(task.assignedTo.id.isNull());
        } else{
            return task.assignedTo.id.in(employeeIds);
        }
    }
}
