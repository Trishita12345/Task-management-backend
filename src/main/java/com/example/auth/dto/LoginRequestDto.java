package com.example.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Name should be within 20 charcters")
    private String username;

    @Pattern(regexp = "^.{8,}$\n",message="Password should be atleast 8 characters")
    private String password;


}
