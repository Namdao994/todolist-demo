package com.lifetex.todolist.modules.todo.dto;

import com.lifetex.todolist.modules.tag.dto.TagResponse;
import com.lifetex.todolist.modules.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object representing a Todo")
public class TodoResponse {

    @Schema(description = "Unique ID of the todo", example = "1")
    private Long id;

    @Schema(description = "Title of the todo", example = "Learn Spring Boot")
    private String title;

    @Schema(description = "Description of the todo", example = "Study Swagger and validation")
    private String description;

    @Schema(
            description = "Current status of the todo",
            example = "PENDING",
            allowableValues = {"PENDING", "IN_PROGRESS", "COMPLETED", "FAILED"}
    )
    private String status;

    @Schema(
            description = "Priority level",
            example = "HIGH",
            allowableValues = {"LOW", "MEDIUM", "HIGH"}
    )
    private String priority;

    @Schema(
            description = "Due date of the todo",
            example = "2026-04-10T23:59:00",
            type = "string",
            format = "date-time"
    )
    private LocalDateTime dueDate;

    @Schema(description = "List of tags associated with this todo")
    private Set<TagResponse> tags;
}