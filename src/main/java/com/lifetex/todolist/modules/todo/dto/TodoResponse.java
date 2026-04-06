package com.lifetex.todolist.modules.todo.dto;

import com.lifetex.todolist.modules.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {
    private String title;

    private String description;

    private String status;

    private String priority;

    private LocalDateTime dueDate;

    private UserResponse user;
}
