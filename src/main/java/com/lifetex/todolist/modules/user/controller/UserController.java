package com.lifetex.todolist.modules.user.controller;

import com.lifetex.todolist.common.ApiResponse;
import com.lifetex.todolist.modules.user.dto.UserChangePasswordRequest;
import com.lifetex.todolist.modules.user.dto.UserCreateRequest;
import com.lifetex.todolist.modules.user.dto.UserResponse;
import com.lifetex.todolist.modules.user.dto.UserUpdateRequest;
import com.lifetex.todolist.modules.user.service.UserService;
import com.lifetex.todolist.modules.user.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(new ApiResponse<>(true, "OK", userService.getAllUsers()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserCreateRequest request){
        UserResponse userResponse = userService.createUser(request);
        ApiResponse<UserResponse> response = new ApiResponse<>(true, "User created successfully", userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        ApiResponse<UserResponse> response = new ApiResponse<>(true, "User Founded", userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request
            ) {
        UserResponse userResponse = userService.updateUser(id, request);
        ApiResponse<UserResponse> response = new ApiResponse<>(true, "User Updated", userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}/change-password")
    public ResponseEntity<ApiResponse<Void>> changePasswordUser(@PathVariable Long id, @Valid @RequestBody UserChangePasswordRequest request) {
        userService.changePasswordUser(id, request);
        ApiResponse<Void> response = new ApiResponse<>(true, "Password Changed", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "User Deleted", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
