package com.lifetex.todolist.modules.tag.dto;

import com.lifetex.todolist.common.validation.ValidEnum;
import com.lifetex.todolist.modules.tag.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Đối tượng request dùng để cập nhật Tag (các field đều không bắt buộc)")
public class TagUpdateRequest {

    @Schema(
            description = "Tên của tag",
            example = "Cá nhân",
            nullable = true
    )
    @Size(min= 3, max = 50, message = "Name must be in 3-50 characters")
    private String name;

    @Schema(
            description = "Màu sắc của tag (BLUE, RED, GREEN)",
            example = "RED",
            allowableValues = {"BLUE", "RED", "GREEN"},
            nullable = true
    )
    @ValidEnum(enumClass = ColorEnum.class, message = "Status must be: BLUE, RED, GREEN")
    private String color;
}