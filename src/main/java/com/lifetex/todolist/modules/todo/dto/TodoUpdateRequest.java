package com.lifetex.todolist.modules.todo.dto;

import com.lifetex.todolist.common.validation.ValidEnum;
import com.lifetex.todolist.modules.todo.enums.PriorityEnum;
import com.lifetex.todolist.modules.todo.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for updating a Todo (all fields are optional)")
public class TodoUpdateRequest {

    @Schema(
            description = "Title of the todo",
            example = "Update Spring Boot project",
            nullable = true
    )
    @Size(min = 3, max = 50, message = "Title must be in 3-50 characters")
    private String title;

    @Schema(
            description = "Description of the todo",
            example = "Update API and fix bugs",
            nullable = true
    )
    @Size(min = 3, message = "Description must be at least 3 characters")
    private String description;

    @Schema(
            description = "Status of the todo",
            example = "IN_PROGRESS",
            allowableValues = {"PENDING", "IN_PROGRESS", "COMPLETED", "FAILED"},
            nullable = true
    )
    @ValidEnum(enumClass = StatusEnum.class, message = "Status must be: PENDING, IN_PROGRESS, COMPLETED, FAILED")
    private String status;

    @Schema(
            description = "Priority level",
            example = "MEDIUM",
            allowableValues = {"LOW", "MEDIUM", "HIGH"},
            nullable = true
    )
    @ValidEnum(enumClass = PriorityEnum.class, message = "Priority must be: LOW, MEDIUM, HIGH")
    private String priority;

    @Schema(
            description = "Due date of the todo (must be in the future)",
            example = "2026-04-10T23:59:00",
            type = "string",
            format = "date-time",
            nullable = true
    )
    @Future(message = "dueDate must be a future date")
    private LocalDateTime dueDate;
}