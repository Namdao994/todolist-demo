package com.lifetex.todolist.modules.auth.service.impl;

import com.lifetex.todolist.common.exception.BusinessException;
import com.lifetex.todolist.common.exception.ResourceNotFoundException;
import com.lifetex.todolist.modules.auth.dto.*;
import com.lifetex.todolist.modules.auth.mapper.AuthMapper;
import com.lifetex.todolist.modules.auth.service.AuthService;
import com.lifetex.todolist.modules.user.entity.UserEntity;
import com.lifetex.todolist.modules.user.mapper.UserMapper;
import com.lifetex.todolist.modules.user.repository.UserRepository;
import com.lifetex.todolist.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    @Override
    public AuthRegisterResponse register(AuthRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("EMAIL_DUPLICATE", "Email already exists");
        }

        //Hash password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        //Tạo user

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setUsername(request.getUsername());

        userRepository.save(user);

        String accessToken = JwtUtil.generateAccessToken(user.getId(), user.getUsername());
        String refreshToken = JwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        return new AuthRegisterResponse(accessToken, refreshToken);
    }

    @Override
    public AuthLoginResponse login(AuthLoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("USER_NOT_FOUND", "User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("INVALID_PASSWORD", "Password is incorrect");
        }

        String accessToken = JwtUtil.generateAccessToken(user.getId(), user.getUsername());
        String refreshToken = JwtUtil.generateRefreshToken(user.getId(), user.getUsername());

        return authMapper.toAuthUserResponse(userMapper.toResponse(user), new AuthRegisterResponse(accessToken, refreshToken));
    }

    @Override
    public AuthRefreshTokenResponse refreshToken(AuthRefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        if(!JwtUtil.isTokenValid(refreshToken)) {
            throw new BusinessException("UNAUTHORIZED", "Refresh token invalid");
        }
        String username = JwtUtil.extractUsername(refreshToken);
        Long userId = JwtUtil.extractUserId(refreshToken);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User not found"));
        String newAccessToken = JwtUtil.generateAccessToken(userId, username);
        String newRefreshToken = JwtUtil.generateAccessToken(userId, username);

        return authMapper.toAuthRefreshTokenResponse(userMapper.toResponse(user), new AuthRegisterResponse(newAccessToken, newRefreshToken));
    }

    @Override
    public void changePassword(Long userId, AuthChangePasswordRequest request) {
        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
