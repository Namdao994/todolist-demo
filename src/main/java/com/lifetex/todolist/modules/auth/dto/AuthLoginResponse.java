package com.lifetex.todolist.modules.auth.dto;

import com.lifetex.todolist.modules.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginResponse {
    private UserResponse user;   // thông tin user
    private AuthRegisterResponse token;
}
