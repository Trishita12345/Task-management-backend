package com.example.auth.repository.predicate;

import com.example.auth.model.QEmployee;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.UUID;

public class EmployeePredicate {
    private static final QEmployee employee = QEmployee.employee;
    public static BooleanExpression findEmployeeByRoleId(UUID roleId){
        return employee.role.id.eq(roleId);
    }
    public static BooleanExpression findByQuery(String query){
        return employee.firstname.containsIgnoreCase(query).or(employee.lastname.containsIgnoreCase(query).or(employee.email.containsIgnoreCase((query))));
    }
}
