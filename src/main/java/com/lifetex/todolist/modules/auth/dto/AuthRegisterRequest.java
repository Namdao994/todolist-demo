package com.lifetex.todolist.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRegisterRequest {
    @NotBlank(message = "Username is required")
    @Size(min=3, max = 20, message = "Username must be 3-20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50, message = "Password must be 6-50 characters")
    private String password;
}
