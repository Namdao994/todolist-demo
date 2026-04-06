package com.lifetex.todolist.modules.todo.service;

import com.lifetex.todolist.modules.tag.dto.TagCreateRequest;
import com.lifetex.todolist.modules.todo.dto.TodoCreateRequest;
import com.lifetex.todolist.modules.todo.dto.TodoResponse;
import com.lifetex.todolist.modules.todo.dto.TodoUpdateRequest;

import java.util.List;

public interface TodoService {

    List<TodoResponse> getAllTodos(Long userId);

    TodoResponse createTodo(TodoCreateRequest request, Long userId);

    TodoResponse getTodoById(Long id, Long userId);

    TodoResponse updateTodo(TodoUpdateRequest request, Long id, Long userId);

    void deleteTodo(Long id, Long userId);

    TodoResponse addTagToTodo(Long id, Long tagId, Long userId);

    TodoResponse removeTagFromTodo(Long id, Long tagId, Long userId);

    TodoResponse createAndAddTagToTodo(TagCreateRequest request, Long id, Long userId);
}
