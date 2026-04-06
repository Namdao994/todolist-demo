package com.lifetex.todolist.modules.tag.service.impl;

import com.lifetex.todolist.common.exception.ResourceNotFoundException;
import com.lifetex.todolist.modules.tag.dto.TagCreateRequest;
import com.lifetex.todolist.modules.tag.dto.TagResponse;
import com.lifetex.todolist.modules.tag.dto.TagUpdateRequest;
import com.lifetex.todolist.modules.tag.entity.TagEntity;
import com.lifetex.todolist.modules.tag.mapper.TagMapper;
import com.lifetex.todolist.modules.tag.repository.TagRepository;
import com.lifetex.todolist.modules.tag.service.TagService;
import com.lifetex.todolist.modules.user.entity.UserEntity;
import com.lifetex.todolist.modules.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final UserRepository userRepository;

    @Override
    public List<TagResponse> getAllTags(Long userId) {
        List<TagEntity> tags = tagRepository.findAllByUserId(userId);
        return tagMapper.toDtoList(tags);
    }

    @Override
    public TagResponse createTag(TagCreateRequest request, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User not found"));
        TagEntity tag = tagMapper.toEntity(request);
        tag.setUser(user);
        tagRepository.save(tag);
        return tagMapper.toResponse(tag);
    }

    @Override
    public TagResponse updateTag(TagUpdateRequest request, Long id,Long userId) {
        var tag = tagRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("TAG_NOT_FOUND", "Tag not found"));
        tagMapper.updateTagFromDto(request, tag);
        tagRepository.save(tag);
        return tagMapper.toResponse(tag);
    }

    @Override
    public void deleteTag(Long id, Long userId) {
        var tag = tagRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("TAG_NOT_FOUND", "Tag not found"));
        tagRepository.delete(tag);
    }
}
