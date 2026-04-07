package com.lifetex.todolist.modules.user.controller;

import com.lifetex.todolist.common.ApiError;
import com.lifetex.todolist.common.ApiResponseData;
import com.lifetex.todolist.common.PageResponse;
import com.lifetex.todolist.modules.user.docs.UserApiSchemas;
import com.lifetex.todolist.modules.user.dto.UserCreateRequest;
import com.lifetex.todolist.modules.user.dto.UserResponse;
import com.lifetex.todolist.modules.user.dto.UserUpdateRequest;
import com.lifetex.todolist.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "User",
        description = "Các API quản lý người dùng: tạo tài khoản, cập nhật thông tin, xem thông tin người dùng"
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "Dữ liệu không hợp lệ",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ApiError.class)
                )
        ),
        @ApiResponse(
                responseCode = "401",
                description = "Chưa xác thực / Token không hợp lệ",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ApiError.class)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Lỗi server",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ApiError.class)
                )
        )
})
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Lấy danh sách tất cả người dùng",
            description = "Truy xuất danh sách tất cả người dùng với phân trang",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Số trang (bắt đầu từ 0)",
                            required = false,
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "Số lượng item trên mỗi trang",
                            required = false,
                            example = "20"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lấy danh sách người dùng thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserApiSchemas.UserPageApiResponse.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<ApiResponseData<PageResponse<UserResponse>>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(new ApiResponseData<>(true, "OK", userService.getAllUsers(pageable)));
    }

    @Operation(
            summary = "Tạo người dùng mới",
            description = "Tạo tài khoản người dùng mới với thông tin trong request body",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Người dùng được tạo thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserApiSchemas.UserApiResponse.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ApiResponseData<UserResponse>> createUser(@Valid @RequestBody UserCreateRequest request){
        UserResponse userResponse = userService.createUser(request);
        ApiResponseData<UserResponse> response = new ApiResponseData<>(true, "User created successfully", userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Lấy thông tin người dùng",
            description = "Truy xuất thông tin chi tiết của người dùng theo id",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID của người dùng",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lấy thông tin người dùng thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserApiSchemas.UserApiResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Người dùng không tồn tại",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseData<UserResponse>> getUser(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        ApiResponseData<UserResponse> response = new ApiResponseData<>(true, "User Founded", userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Operation(
            summary = "Cập nhật người dùng",
            description = "Cập nhật thông tin người dùng theo id",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID của người dùng cần cập nhật",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cập nhật người dùng thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserApiSchemas.UserApiResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Người dùng không tồn tại",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseData<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request
            ) {
        UserResponse userResponse = userService.updateUser(id, request);
        ApiResponseData<UserResponse> response = new ApiResponseData<>(true, "User Updated", userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Xóa người dùng",
            description = "Xóa người dùng theo id",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID của người dùng cần xóa",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Xóa người dùng thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserApiSchemas.UserVoidApiResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Người dùng không tồn tại",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseData<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ApiResponseData<Void> response = new ApiResponseData<>(true, "User Deleted", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
