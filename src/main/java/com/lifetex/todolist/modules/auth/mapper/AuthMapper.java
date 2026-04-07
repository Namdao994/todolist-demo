package com.lifetex.todolist.modules.auth.mapper;

import com.lifetex.todolist.modules.auth.dto.AuthRefreshTokenResponse;
import com.lifetex.todolist.modules.auth.dto.AuthRegisterResponse;
import com.lifetex.todolist.modules.auth.dto.AuthLoginResponse;
import com.lifetex.todolist.modules.user.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    AuthLoginResponse toAuthUserResponse(UserResponse user, AuthRegisterResponse token);

    AuthRefreshTokenResponse toAuthRefreshTokenResponse(UserResponse user, AuthRegisterResponse token);
}