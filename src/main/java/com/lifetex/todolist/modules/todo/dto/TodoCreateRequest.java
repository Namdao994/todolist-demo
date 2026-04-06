package com.lifetex.todolist.modules.todo.dto;

import com.lifetex.todolist.common.validation.ValidEnum;
import com.lifetex.todolist.modules.todo.enums.PriorityEnum;
import com.lifetex.todolist.modules.todo.enums.StatusEnum;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoCreateRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 50, message = "Title must be in 3-50 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min= 3,  message = "Description must be at least 3 characters")
    private String description;

    @NotNull(message = "Status is required")
    @ValidEnum(enumClass = StatusEnum.class, message = "Status must be: PENDING, IN_PROGRESS, COMPLETED, FAILED")
    private String status;

    @NotNull(message = "Priority is required")
    @ValidEnum(enumClass = PriorityEnum.class, message = "Priority must be: LOW, MEDIUM, HIGH")
    private String priority;

    @NotNull(message = "dueDate is required")
    @Future(message = "dueDate must be a future date")
    private LocalDateTime dueDate;

}
