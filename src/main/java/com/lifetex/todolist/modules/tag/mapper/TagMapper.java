package com.lifetex.todolist.modules.tag.mapper;

import com.lifetex.todolist.modules.tag.dto.TagCreateRequest;
import com.lifetex.todolist.modules.tag.dto.TagResponse;
import com.lifetex.todolist.modules.tag.dto.TagUpdateRequest;
import com.lifetex.todolist.modules.tag.entity.TagEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
        TagEntity toEntity(TagCreateRequest request);
        TagResponse toResponse(TagEntity tag);

        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        void updateTagFromDto(TagUpdateRequest dto, @MappingTarget TagEntity tag);
        //Nếu không có @MappingTarget → MapStruct sẽ tạo một object mới
        //Khi update Entity trong JPA → phải update object có sẵn, không tạo mới (để giữ ID, liên kết quan hệ, version…)
        List<TagResponse> toDtoList(List<TagEntity> tags);

}
