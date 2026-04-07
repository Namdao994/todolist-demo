package com.lifetex.todolist.modules.tag.service;

import com.lifetex.todolist.common.PageResponse;
import com.lifetex.todolist.modules.tag.dto.TagCreateRequest;
import com.lifetex.todolist.modules.tag.dto.TagResponse;
import com.lifetex.todolist.modules.tag.dto.TagUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    PageResponse<TagResponse> getAllTags(Long userId, Pageable pageable);

    TagResponse createTag(TagCreateRequest request, Long userId);

    TagResponse updateTag(TagUpdateRequest request, Long id, Long userId);

    void deleteTag(Long id, Long userId);

}
