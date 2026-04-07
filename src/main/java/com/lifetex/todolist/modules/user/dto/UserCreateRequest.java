package com.lifetex.todolist.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "UserCreateRequest", description = "Thông tin dùng để tạo tài khoản mới")
public class UserCreateRequest {

    @Schema(
            description = "Tên đăng nhập",
            example = "namdev",
            minLength = 3,
            maxLength = 20
    )
    @NotBlank(message = "Username must not be blank")
    @Size(min=3, max = 20, message = "Username must be 3-20 characters")
    private String username;

    @Schema(
            description = "Email của người dùng",
            example = "nam@gmail.com"
    )
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @Schema(
            description = "Mật khẩu tài khoản",
            example = "123456",
            minLength = 6,
            maxLength = 50
    )
    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 50, message = "Password must be 6-50 characters")
    private String password;
}