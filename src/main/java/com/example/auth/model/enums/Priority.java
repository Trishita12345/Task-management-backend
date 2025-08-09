package com.example.auth.model.enums;

import lombok.Getter;

@Getter
public enum Priority implements SelectOption<String>{
    P0("P0"),
    P1("P1"),
    P2("P2"),
    P3("P3");

    private final String label;
    private final String value;

    Priority(String label) {
        this.label = label;
        this.value = name();
    }
}

