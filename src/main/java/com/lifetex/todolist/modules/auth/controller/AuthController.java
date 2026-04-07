package com.lifetex.todolist.modules.auth.controller;

import com.lifetex.todolist.common.ApiResponse;
import com.lifetex.todolist.modules.auth.dto.*;
import com.lifetex.todolist.modules.auth.service.AuthService;
import com.lifetex.todolist.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthRegisterResponse>> register(@Valid  @RequestBody AuthRegisterRequest request) {
        ApiResponse<AuthRegisterResponse> response = new ApiResponse<>(true, "Register successfully", authService.register(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthLoginResponse>> login(@Valid @RequestBody AuthLoginRequest request) {
        ApiResponse<AuthLoginResponse> response = new ApiResponse<>(true, "Login successfully", authService.login(request));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthRefreshTokenResponse>> refreshToken(@RequestBody AuthRefreshTokenRequest request) {
        ApiResponse<AuthRefreshTokenResponse> response = new ApiResponse<>(true, "Login successfully", authService.refreshToken(request));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePasswordUser(@Valid @RequestBody AuthChangePasswordRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        authService.changePassword(userId, request);
        ApiResponse<Void> response = new ApiResponse<>(true, "Password Changed", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
