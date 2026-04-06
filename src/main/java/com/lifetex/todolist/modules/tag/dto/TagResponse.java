package com.lifetex.todolist.modules.tag.dto;

import com.lifetex.todolist.modules.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagResponse {
    private Long id;
    private String name;
    private String color;
}
