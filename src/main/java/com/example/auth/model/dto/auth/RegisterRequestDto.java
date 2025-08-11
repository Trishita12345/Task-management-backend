package com.example.auth.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequestDto {

    @NotBlank(message = "Email is required")
    @Pattern(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message="Email is not valid")
    private String email;

    @NotBlank(message = "First Name is required")
    @Size(max = 20, message = "First Name should be within 20 charcters")
    private String firstname;

    @NotBlank(message = "Last Name is required")
    @Size(max = 20, message = "Last Name should be within 20 charcters")
    private String lastname;

    private String profileImage;

    @Pattern(regexp = "^.{8,}$\n",message="Password should be atleast 8 characters")
    private String password;
}
