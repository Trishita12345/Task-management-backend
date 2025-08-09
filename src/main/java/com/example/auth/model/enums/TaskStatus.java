package com.example.auth.model.enums;

import lombok.Getter;

@Getter
public enum TaskStatus implements SelectOption<String>{
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    QA("QA"),
    COMPLETED("Completed");

    private final String label;
    private final String value;

    TaskStatus(String label) {
        this.label = label;
        this.value = name();
    }

}

