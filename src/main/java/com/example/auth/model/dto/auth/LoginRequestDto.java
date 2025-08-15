package com.example.auth.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @Pattern(regexp = "^.{8,}$",message="Password should be atleast 8 characters")
    private String password;

}
