package com.lifetex.todolist.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRefreshTokenResponse {
    @Schema(description = "Token truy cập (JWT) của người dùng", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Token làm mới (Refresh Token) của người dùng", example = "dGhpc2lzYXJlZnJlc2h0b2tlbg==")
    private String refreshToken;
}
