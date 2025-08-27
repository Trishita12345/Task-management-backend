package com.example.auth.model.enums;

import lombok.Getter;

@Getter
public enum TaskStatus implements SelectOption<String>{
    TODO("NEW"),
    IN_PROGRESS("IN PROGRESS"),
    QA("QA"),
    COMPLETED("CLOSED");

    private final String label;
    private final String value;

    TaskStatus(String label) {
        this.label = label;
        this.value = name();
    }

}

