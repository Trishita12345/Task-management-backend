package com.example.auth.model.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDTO {
    @NotBlank
    @Size(max = 500, message = "Comment length cannot be more than 500 characters")
    private String content;
}
