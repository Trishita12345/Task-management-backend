package com.example.auth.repository.predicate;

import com.example.auth.model.QRole;
import com.querydsl.core.types.dsl.BooleanExpression;

public class RolePredicate {
    private static final QRole role = QRole.role;

    public static BooleanExpression findByQuery(String query) {
        return role.name.containsIgnoreCase(query);
    }


}
