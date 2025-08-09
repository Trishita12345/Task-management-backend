package com.example.auth.model.enums;

import lombok.Getter;

@Getter
public enum TaskType implements SelectOption<String>{
    STORY("Story"),
    TASK("Task"),
    BUG("Bug"),
    ENHANCEMENT("Enhancement");

    private final String label;
    private final String value;

    TaskType(String label) {
        this.label = label;
        this.value = name();
    }
}
