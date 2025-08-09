package com.example.auth.repository.predicate;

import com.example.auth.model.QProject;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.UUID;

public class ProjectPredicate {
    private static final QProject project = QProject.project;

    public static BooleanExpression findByQuery(String query) {
            return project.name.containsIgnoreCase(query).or(project.details.containsIgnoreCase(query));
    }

    public static BooleanExpression findByEmployeeId(UUID employeeId){
        return project.employees.any().id.eq(employeeId);
    }


}
