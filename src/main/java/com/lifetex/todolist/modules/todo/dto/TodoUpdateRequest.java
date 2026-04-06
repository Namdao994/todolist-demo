package com.lifetex.todolist.modules.todo.dto;

import com.lifetex.todolist.common.validation.ValidEnum;
import com.lifetex.todolist.modules.todo.enums.PriorityEnum;
import com.lifetex.todolist.modules.todo.enums.StatusEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoUpdateRequest {
    @Size(min = 3, max = 50, message = "Title must be in 3-50 characters")
    private String title;

    @Size(min= 3,  message = "Description must be at least 3 characters")
    private String description;

    @ValidEnum(enumClass = StatusEnum.class, message = "Status must be: PENDING, IN_PROGRESS, COMPLETED, FAILED")
    private String status;

    @ValidEnum(enumClass = PriorityEnum.class, message = "Priority must be: LOW, MEDIUM, HIGH")
    private String priority;

    @Future(message = "dueDate must be a future date")
    private LocalDateTime dueDate;
}
