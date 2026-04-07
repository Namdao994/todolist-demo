package com.lifetex.todolist.modules.auth.dto;

import com.lifetex.todolist.modules.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginResponse {
    @Schema(description = "Thông tin người dùng đăng nhập")
private UserResponse user;

    @Schema(description = "Các token của người dùng (accessToken và refreshToken)")
    private AuthRegisterResponse token;
}
