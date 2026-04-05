package com.lifetex.todolist.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserCreateRequest {
    @NotBlank(message = "Username must not be blank")
    @Size(min=3, max = 20, message = "Username must be 3-20 characters")
    private String username;

    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 50, message = "Password must be 6-50 characters")
    private String password;

}
