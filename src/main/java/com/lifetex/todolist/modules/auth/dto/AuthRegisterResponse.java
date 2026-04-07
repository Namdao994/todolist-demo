    package com.lifetex.todolist.modules.auth.dto;

    import lombok.AllArgsConstructor;
    import lombok.Data;

    @Data
    @AllArgsConstructor
    public class AuthRegisterResponse {
        private String accessToken;
        private String refreshToken;
    }
