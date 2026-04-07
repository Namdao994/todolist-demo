package com.lifetex.todolist.modules.tag.dto;

import com.lifetex.todolist.common.validation.ValidEnum;
import com.lifetex.todolist.modules.tag.enums.ColorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Đối tượng request dùng để tạo mới Tag")
public class TagCreateRequest {

    @Schema(
            description = "Tên của tag",
            example = "Công việc"
    )
    @NotBlank(message = "Name is required")
    @Size(min= 3, max = 50, message = "Name must be in 3-50 characters")
    private String name;

    @Schema(
            description = "Màu sắc của tag",
            example = "BLUE",
            allowableValues = {"BLUE", "RED", "GREEN"}
    )
    @NotNull(message = "Color is required")
    @ValidEnum(enumClass = ColorEnum.class, message = "Status must be: BLUE, RED, GREEN")
    private String color;
}