package com.lifetex.todolist.modules.todo.repository;

import com.lifetex.todolist.modules.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    Optional<TodoEntity> findByIdAndUserId(Long id, Long userId);
    List<TodoEntity> findAllByUserId(Long userId);
}
