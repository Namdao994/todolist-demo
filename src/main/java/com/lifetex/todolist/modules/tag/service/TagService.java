package com.lifetex.todolist.modules.tag.service;

import com.lifetex.todolist.modules.tag.dto.TagCreateRequest;
import com.lifetex.todolist.modules.tag.dto.TagResponse;
import com.lifetex.todolist.modules.tag.dto.TagUpdateRequest;

import java.util.List;

public interface TagService {

    List<TagResponse> getAllTags(Long userId);

    TagResponse createTag(TagCreateRequest request, Long userId);

    TagResponse updateTag(TagUpdateRequest request, Long id, Long userId);

    void deleteTag(Long id, Long userId);

}
