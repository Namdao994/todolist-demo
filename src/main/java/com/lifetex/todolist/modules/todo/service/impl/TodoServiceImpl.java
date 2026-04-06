package com.lifetex.todolist.modules.todo.service.impl;

import com.lifetex.todolist.common.exception.ResourceNotFoundException;
import com.lifetex.todolist.modules.tag.dto.TagCreateRequest;
import com.lifetex.todolist.modules.tag.entity.TagEntity;
import com.lifetex.todolist.modules.tag.mapper.TagMapper;
import com.lifetex.todolist.modules.tag.repository.TagRepository;
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
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

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

    @Override
    public TodoResponse addTagToTodo(Long id, Long tagId, Long userId) {
        TodoEntity todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("TODO_NOT_FOUND", "Todo not found"));
        TagEntity tag = tagRepository.findByIdAndUserId(tagId, userId).orElseThrow(() -> new ResourceNotFoundException("TAG_NOT_FOUND", "Tag not found"));
        todo.getTags().add(tag);
        todoRepository.save(todo);
        return todoMapper.toResponse(todo);
    }

    @Override
    public TodoResponse removeTagFromTodo(Long id, Long tagId, Long userId) {
        TodoEntity todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("TODO_NOT_FOUND", "Todo not found"));
        todo.getTags().removeIf(tag -> tag.getId().equals(tagId));
        todoRepository.save(todo);
        return todoMapper.toResponse(todo);
    }

    @Override
    public TodoResponse createAndAddTagToTodo(TagCreateRequest request, Long id, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND", "User not found"));
        TodoEntity todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("TODO_NOT_FOUND", "Todo not found"));
        TagEntity tag = tagMapper.toEntity(request);
        tag.setUser(user);
        tagRepository.save(tag);
        todo.getTags().add(tag);
        todoRepository.save(todo);
        return todoMapper.toResponse(todo);
    }
}
