package com.lifetex.todolist.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
}
