package com.lifetex.todolist.modules.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Đối tượng response trả về thông tin Tag")
public class TagResponse {

    @Schema(
            description = "ID của tag",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Tên của tag",
            example = "Công việc"
    )
    private String name;

    @Schema(
            description = "Màu sắc của tag (BLUE, RED, GREEN)",
            example = "BLUE"
    )
    private String color;
}