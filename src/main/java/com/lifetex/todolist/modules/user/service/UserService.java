package com.lifetex.todolist.modules.user.service;

import com.lifetex.todolist.common.PageResponse;
import com.lifetex.todolist.modules.user.dto.UserCreateRequest;
import com.lifetex.todolist.modules.user.dto.UserResponse;
import com.lifetex.todolist.modules.user.dto.UserUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    PageResponse<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserById(Long id);

    UserResponse createUser(UserCreateRequest request);

    UserResponse updateUser(Long id, UserUpdateRequest request);

    void deleteUser(Long id);
}
