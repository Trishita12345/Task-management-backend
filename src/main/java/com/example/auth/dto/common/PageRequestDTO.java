package com.example.auth.dto.common;

import lombok.Data;

@Data
public class PageRequestDTO {
    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String direction = "desc";
}
