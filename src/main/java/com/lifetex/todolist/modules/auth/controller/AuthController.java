package com.lifetex.todolist.modules.auth.controller;

import com.lifetex.todolist.common.ApiError;
import com.lifetex.todolist.common.ApiResponseData;
import com.lifetex.todolist.modules.auth.docs.AuthApiSchemas;
import com.lifetex.todolist.modules.auth.dto.*;
import com.lifetex.todolist.modules.auth.service.AuthService;
import com.lifetex.todolist.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Các API xác thực người dùng")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Lỗi server", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))
})
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Đăng ký tài khoản mới",
            description = "Tạo tài khoản mới với username, email và password"
            ,responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Đăng nhập thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthApiSchemas.AuthRegisterApiResponse.class)
                    )
            )}
    )
    @PostMapping("/register")
    public ResponseEntity<ApiResponseData<AuthRegisterResponse>> register(@Valid  @RequestBody AuthRegisterRequest request) {
        ApiResponseData<AuthRegisterResponse> response = new ApiResponseData<>(true, "Register successfully", authService.register(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Đăng nhập",
            description = "Đăng nhập bằng email và password",
            tags = {"Auth"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Đăng nhập thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthApiSchemas.AuthLoginApiResponse.class)
                            )
                    )

            }
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponseData<AuthLoginResponse>> login(@Valid @RequestBody AuthLoginRequest request) {
        ApiResponseData<AuthLoginResponse> response = new ApiResponseData<>(true, "Login successfully", authService.login(request));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Làm mới JWT token", description = "Nhận token mới dựa trên refresh token",  responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Làm mới token thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthApiSchemas.AuthRefreshTokenApiResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực / Token không hợp lệ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),

    })
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponseData<AuthRefreshTokenResponse>> refreshToken(@RequestBody AuthRefreshTokenRequest request) {
        ApiResponseData<AuthRefreshTokenResponse> response = new ApiResponseData<>(true, "Login successfully", authService.refreshToken(request));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Đổi mật khẩu", description = "Người dùng hiện tại đổi mật khẩu, cần JWT", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Đổi mật khẩu thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseData.class) // data=null
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực / Token không hợp lệ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),

    })
    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponseData<Void>> changePasswordUser(@Valid @RequestBody AuthChangePasswordRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        authService.changePassword(userId, request);
        ApiResponseData<Void> response = new ApiResponseData<>(true, "Password Changed", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
