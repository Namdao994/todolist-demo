package com.lifetex.todolist.modules.tag.dto;


import com.lifetex.todolist.common.validation.ValidEnum;
import com.lifetex.todolist.modules.tag.enums.ColorEnum;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagUpdateRequest {
    @Size(min= 3, max = 50, message = "Name must be in 3-50 characters")
    private String name;

    @ValidEnum(enumClass = ColorEnum.class, message = "Status must be: BLUE, RED, GREEN")
    private String color;
}
