package com.lifetex.todolist.modules.user.mapper;

import com.lifetex.todolist.modules.user.dto.UserCreateRequest;
import com.lifetex.todolist.modules.user.dto.UserUpdateRequest;
import com.lifetex.todolist.modules.user.dto.UserResponse;
import com.lifetex.todolist.modules.user.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(UserCreateRequest request);
    UserResponse toResponse(UserEntity user);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateRequest dto, @MappingTarget UserEntity user);
    //Nếu không có @MappingTarget → MapStruct sẽ tạo một object mới
    //Khi update Entity trong JPA → phải update object có sẵn, không tạo mới (để giữ ID, liên kết quan hệ, version…)
    List<UserResponse> toDtoList(List<UserEntity> users);
}
