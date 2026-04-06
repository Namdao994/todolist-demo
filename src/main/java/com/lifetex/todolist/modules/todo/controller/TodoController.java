package com.lifetex.todolist.modules.todo.controller;

import com.lifetex.todolist.common.ApiResponse;
import com.lifetex.todolist.modules.todo.dto.TodoCreateRequest;
import com.lifetex.todolist.modules.todo.dto.TodoResponse;
import com.lifetex.todolist.modules.todo.dto.TodoUpdateRequest;
import com.lifetex.todolist.modules.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TodoResponse>>> getAllTodos(@RequestParam Long userId) {
        var todos = todoService.getAllTodos(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ok", todos));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TodoResponse>> createTodo(@RequestParam Long userId,@Valid @RequestBody TodoCreateRequest request) {
        TodoResponse todoResponse = todoService.createTodo(request, userId);
        ApiResponse<TodoResponse> response = new ApiResponse<>(true, "Todo created successfully", todoResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoResponse>> getTodo(@PathVariable Long id, @RequestParam Long userId) {
        TodoResponse todoResponse = todoService.getTodoById(id, userId);
        ApiResponse<TodoResponse> response = new ApiResponse<>(true, "Todo Founded", todoResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TodoResponse>> updateTodo(@PathVariable Long id, @RequestParam Long userId, @Valid @RequestBody TodoUpdateRequest request) {
        TodoResponse todoResponse = todoService.updateTodo(request, id, userId);
        ApiResponse<TodoResponse> response = new ApiResponse<>(true, "Todo Updated", todoResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTodo(@PathVariable Long id, @RequestParam Long userId) {
        todoService.deleteTodo(id, userId);
        ApiResponse<Void> response = new ApiResponse<>(true, "Todo Deleted", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
