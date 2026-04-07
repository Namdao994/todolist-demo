package com.lifetex.todolist.modules.tag.controller;

import com.lifetex.todolist.common.ApiError;
import com.lifetex.todolist.common.ApiResponseData;
import com.lifetex.todolist.common.PageResponse;
import com.lifetex.todolist.modules.tag.docs.TagApiSchemas;
import com.lifetex.todolist.modules.tag.dto.TagCreateRequest;
import com.lifetex.todolist.modules.tag.dto.TagResponse;
import com.lifetex.todolist.modules.tag.dto.TagUpdateRequest;
import com.lifetex.todolist.modules.tag.service.TagService;
import com.lifetex.todolist.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Tag",
        description = "Các API quản lý thẻ phân loại công việc (tag): tạo mới, cập nhật, xóa và lấy danh sách tag theo người dùng"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "401", description = "Chưa xác thực / Token không hợp lệ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Lỗi server", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))
})
@RestController
@RequestMapping("/api/v1/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;
    @Operation(
            summary = "Lấy danh sách tất cả Tags của người dùng",
            description = "Truy xuất toàn bộ danh sách Tag của người dùng hiện tại",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Số trang (bắt đầu từ 0)",
                            required = false,
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "Số lượng item trên mỗi trang",
                            required = false,
                            example = "20"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lấy danh sách Tags thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TagApiSchemas.TagPageApiResponse.class)
                    )
            ),
    })
    @GetMapping
    public ResponseEntity<ApiResponseData<PageResponse<TagResponse>>> getAllTags(@RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "20") int size) {
        Long userId = SecurityUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<TagResponse> tags = tagService.getAllTags(userId, pageable);
        return ResponseEntity.ok(new ApiResponseData<>(true, "OK", tags));
    }


    @Operation(
            summary = "Tạo Tag mới",
            description = "Tạo một Tag mới cho người dùng hiện tại"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tạo Tag thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TagApiSchemas.TagApiResponse.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponseData<TagResponse>> createTag(@Valid @RequestBody TagCreateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        TagResponse tag = tagService.createTag(request, userId);
        ApiResponseData<TagResponse> response = new ApiResponseData<>(true, "Tag created successfully", tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Cập nhật Tag",
            description = "Cập nhật thông tin Tag theo id cho người dùng hiện tại"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cập nhật Tag thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TagApiSchemas.TagApiResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tag không tồn tại",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseData<TagResponse>> updateTag(@PathVariable Long id, @Valid @RequestBody TagUpdateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        TagResponse tag = tagService.updateTag(request, id, userId);
        ApiResponseData<TagResponse> response = new ApiResponseData<>(true, "Tag updated successfully", tag);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Xóa Tag",
            description = "Xóa Tag theo id của người dùng hiện tại"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Xóa Tag thành công",
                    content = @Content(
                            mediaType = "application/json",
                                schema = @Schema(implementation = TagApiSchemas.TagVoidApiResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tag không tồn tại",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseData<Void>> deleteTag(@PathVariable Long id) {
        Long userId = SecurityUtil.getCurrentUserId();
        tagService.deleteTag(id, userId);
        ApiResponseData<Void> response = new ApiResponseData<>(true, "Tag deleted successfully", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
