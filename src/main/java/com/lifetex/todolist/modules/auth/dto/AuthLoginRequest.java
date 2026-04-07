package com.lifetex.todolist.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginRequest {
    @Schema(description = "Email của người dùng, phải hợp lệ", example = "namdao@gmail.com")
    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @Schema(description = "Mật khẩu người dùng, từ 6-50 ký tự", example = "12345678")
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50, message = "Password must be 6-50 characters")
    private String password;
}
