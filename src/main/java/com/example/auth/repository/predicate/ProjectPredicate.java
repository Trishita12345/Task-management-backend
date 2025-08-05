package com.example.auth.repository.predicate;

import com.example.auth.model.QProject;
import com.querydsl.core.types.dsl.BooleanExpression;

public class ProjectPredicate {
    private static final QProject project = QProject.project;

    public static BooleanExpression findByQuery(String name, String details) {
            return project.name.containsIgnoreCase(name).or(project.details.containsIgnoreCase(details));
    }

}
