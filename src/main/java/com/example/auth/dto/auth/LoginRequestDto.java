package com.example.auth.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "Email is required")
    @Size(max = 20, message = "Email should be within 20 charcters")
    private String email;

    @Pattern(regexp = "^.{8,}$\n",message="Password should be atleast 8 characters")
    private String password;


}
