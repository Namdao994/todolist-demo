package com.lifetex.todolist.modules.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lifetex.todolist.common.validation.ValidEnum;
import com.lifetex.todolist.modules.todo.enums.PriorityEnum;
import com.lifetex.todolist.modules.todo.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Request object for creating a new Todo")
public class TodoCreateRequest {

    @Schema(description = "Title of the todo", example = "Learn Spring Boot")
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 50, message = "Title must be in 3-50 characters")
    private String title;

    @Schema(description = "Detailed description of the todo", example = "Study Swagger and validation")
    @NotBlank(message = "Description is required")
    @Size(min= 3, message = "Description must be at least 3 characters")
    private String description;

    @Schema(
            description = "Status of the todo",
            example = "PENDING",
            allowableValues = {"PENDING", "IN_PROGRESS", "COMPLETED", "FAILED"}
    )
    @NotNull(message = "Status is required")
    @ValidEnum(enumClass = StatusEnum.class, message = "Status must be: PENDING, IN_PROGRESS, COMPLETED, FAILED")
    private String status;

    @Schema(
            description = "Priority level",
            example = "HIGH",
            allowableValues = {"LOW", "MEDIUM", "HIGH"}
    )
    @NotNull(message = "Priority is required")
    @ValidEnum(enumClass = PriorityEnum.class, message = "Priority must be: LOW, MEDIUM, HIGH")
    private String priority;

    @Schema(
            description = "Due date of the todo (must be in the future)",
            example = "2026-04-10T23:59:00"
    )
    @NotNull(message = "dueDate is required")
    @Future(message = "dueDate must be a future date")
    private LocalDateTime dueDate;

}