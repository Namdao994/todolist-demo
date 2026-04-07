package com.lifetex.todolist.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthChangePasswordRequest {

    @Schema(description = "Mật khẩu người dùng, từ 6-50 ký tự", example = "12345678")
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50, message = "Password must be 6-50 characters")
    private String currentPassword;

    @Schema(description = "Mật khẩu người dùng, từ 6-50 ký tự", example = "12345678")
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50, message = "Password must be 6-50 characters")
    private String newPassword;
}
