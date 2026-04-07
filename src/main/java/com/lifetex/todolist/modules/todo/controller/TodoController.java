package com.lifetex.todolist.modules.todo.controller;

import com.lifetex.todolist.common.ApiError;
import com.lifetex.todolist.common.ApiResponseData;
import com.lifetex.todolist.common.PageResponse;
import com.lifetex.todolist.modules.tag.dto.TagCreateRequest;
import com.lifetex.todolist.modules.todo.docs.TodoApiSchemas;
import com.lifetex.todolist.modules.todo.dto.TodoCreateRequest;
import com.lifetex.todolist.modules.todo.dto.TodoResponse;
import com.lifetex.todolist.modules.todo.dto.TodoUpdateRequest;
import com.lifetex.todolist.modules.todo.service.TodoService;
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

@Tag(name = "Todo", description = "Các API quản lý danh sách công việc: tạo, cập nhật, xóa, xem chi tiết và danh sách todos")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "401", description = "Chưa xác thực / Token không hợp lệ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "500", description = "Lỗi server", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ApiError.class)))
})
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {
    private final TodoService todoService;

    @Operation(
            summary = "Lấy danh sách tất cả Todos của người dùng",
            description = "Truy xuất danh sách Todos của người dùng hiện tại với phân trang",
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
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lấy danh sách Todos thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TodoApiSchemas.TodoPageApiResponse.class)
                            )
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<ApiResponseData<PageResponse<TodoResponse>>> getAllTodos(@RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "20") int size) {
        Long userId = SecurityUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size);
        var todos = todoService.getAllTodos(userId, pageable);
        return ResponseEntity.ok(new ApiResponseData<>(true, "Ok", todos));
    }

    @Operation(
            summary = "Tạo một Todo mới",
            description = "Tạo Todo mới cho người dùng hiện tại với các thông tin trong request body",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Todo được tạo thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TodoApiSchemas.TodoApiResponse.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ApiResponseData<TodoResponse>> createTodo(@Valid @RequestBody TodoCreateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        TodoResponse todoResponse = todoService.createTodo(request, userId);
        ApiResponseData<TodoResponse> response = new ApiResponseData<>(true, "Todo created successfully", todoResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Lấy thông tin Todo theo ID",
            description = "Truy xuất Todo của người dùng hiện tại dựa vào ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID của Todo cần lấy",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Todo được tìm thấy thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TodoApiSchemas.TodoApiResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Todo không tồn tại",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseData<TodoResponse>> getTodo(@PathVariable Long id) {
        Long userId = SecurityUtil.getCurrentUserId();
        TodoResponse todoResponse = todoService.getTodoById(id, userId);
        ApiResponseData<TodoResponse> response = new ApiResponseData<>(true, "Todo Founded", todoResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Cập nhật Todo theo ID",
            description = "Cập nhật thông tin Todo của người dùng hiện tại dựa vào ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID của Todo cần cập nhật",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Todo được cập nhật thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TodoApiSchemas.TodoApiResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Todo không tồn tại",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseData<TodoResponse>> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoUpdateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        TodoResponse todoResponse = todoService.updateTodo(request, id, userId);
        ApiResponseData<TodoResponse> response = new ApiResponseData<>(true, "Todo Updated", todoResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Xóa Todo theo ID",
            description = "Xóa Todo của người dùng hiện tại dựa vào ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID của Todo cần xóa",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Todo được xóa thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponseData.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Todo không tồn tại",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseData<Void>> deleteTodo(@PathVariable Long id) {
        Long userId = SecurityUtil.getCurrentUserId();
        todoService.deleteTodo(id, userId);
        ApiResponseData<Void> response = new ApiResponseData<>(true, "Todo Deleted", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Thêm Tag vào Todo",
            description = "Thêm một Tag vào Todo của người dùng hiện tại dựa vào ID Todo và ID Tag",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID của Todo",
                            required = true,
                            example = "1"
                    ),
                    @Parameter(
                            name = "tagId",
                            description = "ID của Tag cần thêm vào Todo",
                            required = true,
                            example = "2"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag được thêm vào Todo thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TodoApiSchemas.TodoApiResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Todo hoac Tag không tồn tại",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @PostMapping("/{id}/tags/{tagId}")
    public ResponseEntity<ApiResponseData<TodoResponse>> AddTagToTodo(@PathVariable Long id, @PathVariable Long tagId) {
        Long userId = SecurityUtil.getCurrentUserId();
        TodoResponse todo = todoService.addTagToTodo(id, tagId, userId);
        ApiResponseData<TodoResponse> response = new ApiResponseData<>(true, "OK", todo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Xóa Tag khỏi Todo",
            description = "Xóa một Tag khỏi Todo của người dùng hiện tại dựa vào ID Todo và ID Tag",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID của Todo",
                            required = true,
                            example = "1"
                    ),
                    @Parameter(
                            name = "tagId",
                            description = "ID của Tag cần xóa khỏi Todo",
                            required = true,
                            example = "2"
                    ),

            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag được xóa khỏi Todo thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TodoApiSchemas.TodoApiResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Todo hoac Tag không tồn tại",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @DeleteMapping("/{id}/tags/{tagId}")
    public ResponseEntity<ApiResponseData<TodoResponse>> removeTagFromTodo(@PathVariable Long id, @PathVariable Long tagId) {
        Long userId = SecurityUtil.getCurrentUserId();
       TodoResponse todo = todoService.removeTagFromTodo(id, tagId, userId);
         ApiResponseData<TodoResponse> response = new ApiResponseData<>(true, "OK", todo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Tạo Tag mới và thêm vào Todo",
            description = "Tạo một Tag mới và tự động thêm vào Todo của người dùng hiện tại dựa vào ID Todo",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID của Todo",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag được tạo và thêm vào Todo thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TodoApiSchemas.TodoApiResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Todo không tồn tại",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    @PostMapping("/{id}/tags")
    public ResponseEntity<ApiResponseData<TodoResponse>> createAndAddTagToTodo(@PathVariable Long id, @Valid @RequestBody TagCreateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        TodoResponse todo = todoService.createAndAddTagToTodo(request, id, userId);
        ApiResponseData<TodoResponse> response = new ApiResponseData<>(true, "OK", todo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
