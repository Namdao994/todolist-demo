package com.lifetex.todolist.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRefreshTokenRequest {
    @Schema(description = "Token làm mới (Refresh Token) của người dùng", example = "dGhpc2lzYXJlZnJlc2h0b2tlbg==")
    private String refreshToken;
}
