package com.lifetex.todolist.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthChangePasswordRequest {
    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 50, message = "Password must be 6-50 characters")
    private String currentPassword;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 50, message = "Password must be 6-50 characters")
    private String newPassword;
}
