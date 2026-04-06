package com.lifetex.todolist.modules.todo.service.impl;

import com.lifetex.todolist.common.exception.ResourceNotFoundException;
import com.lifetex.todolist.modules.todo.dto.TodoCreateRequest;
import com.lifetex.todolist.modules.todo.dto.TodoResponse;
import com.lifetex.todolist.modules.todo.dto.TodoUpdateRequest;
import com.lifetex.todolist.modules.todo.entity.TodoEntity;
import com.lifetex.todolist.modules.todo.mapper.TodoMapper;
import com.lifetex.todolist.modules.todo.repository.TodoRepository;
import com.lifetex.todolist.modules.todo.service.TodoService;
import com.lifetex.todolist.modules.user.entity.UserEntity;
import com.lifetex.todolist.modules.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final UserRepository userRepository;

    @Override
    public List<TodoResponse> getAllTodos(Long userId) {
        List<TodoEntity> todos = todoRepository.findAllByUserId(userId);
        return todoMapper.toDtoList(todos);
    }

    @Override
    public TodoResponse createTodo(TodoCreateRequest request, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User not found"));
        TodoEntity todo = todoMapper.toEntity(request);
        todo.setUser(user);
        todoRepository.save(todo);
        return todoMapper.toResponse(todo);
    }

    @Override
    public TodoResponse getTodoById(Long id, Long userId) {
        var todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("TODO_NOT_FOUND", "Todo not found"));
        return todoMapper.toResponse(todo);
    }

    @Override
    public TodoResponse updateTodo(TodoUpdateRequest request, Long id, Long userId) {
        System.out.println("Request >>>> " + request);
        var todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("TODO_NOT_FOUND", "Todo not found"));
        todoMapper.updateTodoFromDto(request, todo);
        todoRepository.save(todo);
        return todoMapper.toResponse(todo);
    }

    @Override
    public void deleteTodo(Long id, Long userId) {
        var todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("TODO_NOT_FOUND", "Todo not found"));
        todoRepository.delete(todo);
    }
}
