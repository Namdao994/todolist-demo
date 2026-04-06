package com.lifetex.todolist.modules.tag.controller;

import com.lifetex.todolist.common.ApiResponse;
import com.lifetex.todolist.modules.tag.dto.TagCreateRequest;
import com.lifetex.todolist.modules.tag.dto.TagResponse;
import com.lifetex.todolist.modules.tag.dto.TagUpdateRequest;
import com.lifetex.todolist.modules.tag.service.TagService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTags(@RequestParam Long userId) {
        List<TagResponse> tags = tagService.getAllTags(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "OK", tags));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TagResponse>> createTag(@RequestParam Long userId, @Valid @RequestBody TagCreateRequest request) {
        TagResponse tag = tagService.createTag(request, userId);
        ApiResponse<TagResponse> response = new ApiResponse<>(true, "Tag created successfully", tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> updateTag(@PathVariable Long id,@RequestParam Long userId, @Valid @RequestBody TagUpdateRequest request) {
        TagResponse tag = tagService.updateTag(request, id, userId);
        ApiResponse<TagResponse> response = new ApiResponse<>(true, "Tag updated successfully", tag);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTag(@PathVariable Long id, @RequestParam Long userId) {
        tagService.deleteTag(id, userId);
        ApiResponse<Void> response = new ApiResponse<>(true, "Tag deleted successfully", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
