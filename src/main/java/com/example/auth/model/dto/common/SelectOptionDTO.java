package com.example.auth.model.dto.common;

import lombok.Data;

@Data
public class SelectOptionDTO<T> {
    private String label;
    private T value;

    public SelectOptionDTO(String label, T value) {
        this.label = label;
        this.value = value;
    }
}
