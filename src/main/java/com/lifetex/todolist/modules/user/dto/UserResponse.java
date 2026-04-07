package com.lifetex.todolist.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserResponse", description = "Thông tin trả về của người dùng")
public class UserResponse {

    @Schema(
            description = "ID của người dùng",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Tên đăng nhập",
            example = "namdev"
    )
    private String username;

    @Schema(
            description = "Email của người dùng",
            example = "nam@gmail.com"
    )
    private String email;
}