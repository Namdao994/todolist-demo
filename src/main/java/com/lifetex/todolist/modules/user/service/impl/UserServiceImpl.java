package com.lifetex.todolist.modules.user.service.impl;

import com.lifetex.todolist.common.exception.ResourceNotFoundException;
import com.lifetex.todolist.modules.user.dto.UserChangePasswordRequest;
import com.lifetex.todolist.modules.user.dto.UserCreateRequest;
import com.lifetex.todolist.modules.user.dto.UserResponse;
import com.lifetex.todolist.modules.user.dto.UserUpdateRequest;
import com.lifetex.todolist.modules.user.entity.UserEntity;
import com.lifetex.todolist.modules.user.mapper.UserMapper;
import com.lifetex.todolist.modules.user.repository.UserRepository;
import com.lifetex.todolist.modules.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<UserResponse> getAllUsers() {
        var users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @Override
    public UserResponse getUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User not found"));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        UserEntity user = userMapper.toEntity(request);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User not found"));
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        userMapper.updateUserFromDto(request, user);
        userRepository.save(user);
        return userMapper.toResponse(user);

    }

    @Override
    public void changePasswordUser(Long id, UserChangePasswordRequest request) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


    @Override
    public void deleteUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User not found"));
        userRepository.delete(user);
    }
}
