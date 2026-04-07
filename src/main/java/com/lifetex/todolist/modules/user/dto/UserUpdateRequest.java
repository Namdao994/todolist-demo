package com.lifetex.todolist.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserUpdateRequest", description = "Thông tin dùng để cập nhật người dùng")
public class UserUpdateRequest {

    @Schema(
            description = "Tên đăng nhập mới",
            example = "namdev123",
            minLength = 3,
            maxLength = 20
    )
    @NotBlank(message = "Username must not be blank")
    @Size(min=3, max = 20, message = "Username must be 3-20 characters")
    private String username;
}