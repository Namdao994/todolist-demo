package com.lifetex.todolist.modules.auth.service;

import com.lifetex.todolist.modules.auth.dto.*;

public interface AuthService {
    AuthRegisterResponse register(AuthRegisterRequest request);

    AuthLoginResponse login(AuthLoginRequest request);

    AuthRefreshTokenResponse refreshToken(AuthRefreshTokenRequest request);

    void changePassword(Long userId, AuthChangePasswordRequest request);
}
