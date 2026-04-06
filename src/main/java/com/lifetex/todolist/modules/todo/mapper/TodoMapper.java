package com.lifetex.todolist.modules.todo.mapper;

import com.lifetex.todolist.modules.todo.dto.TodoCreateRequest;
import com.lifetex.todolist.modules.todo.dto.TodoResponse;
import com.lifetex.todolist.modules.todo.dto.TodoUpdateRequest;
import com.lifetex.todolist.modules.todo.entity.TodoEntity;
import com.lifetex.todolist.modules.user.dto.UserCreateRequest;
import com.lifetex.todolist.modules.user.dto.UserResponse;
import com.lifetex.todolist.modules.user.dto.UserUpdateRequest;
import com.lifetex.todolist.modules.user.entity.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoEntity toEntity(TodoCreateRequest request);
    TodoResponse toResponse(TodoEntity todo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTodoFromDto(TodoUpdateRequest dto, @MappingTarget TodoEntity todo);
    //Nếu không có @MappingTarget → MapStruct sẽ tạo một object mới
    //Khi update Entity trong JPA → phải update object có sẵn, không tạo mới (để giữ ID, liên kết quan hệ, version…)
    List<TodoResponse> toDtoList(List<TodoEntity> todos);
}
