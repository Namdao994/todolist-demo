package com.lifetex.todolist.modules.user.service;

import com.lifetex.todolist.modules.user.dto.UserChangePasswordRequest;
import com.lifetex.todolist.modules.user.dto.UserCreateRequest;
import com.lifetex.todolist.modules.user.dto.UserResponse;
import com.lifetex.todolist.modules.user.dto.UserUpdateRequest;
import com.lifetex.todolist.modules.user.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse createUser(UserCreateRequest request);

    UserResponse updateUser(Long id, UserUpdateRequest request);

    void changePasswordUser(Long id, UserChangePasswordRequest request);

    void deleteUser(Long id);
}
