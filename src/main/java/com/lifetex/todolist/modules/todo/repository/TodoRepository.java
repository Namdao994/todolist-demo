package com.lifetex.todolist.modules.todo.repository;

import com.lifetex.todolist.modules.todo.entity.TodoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    Page<TodoEntity> findAllByUserId(Long userId, Pageable pageable);
    Optional<TodoEntity> findByIdAndUserId(Long id, Long userId);
}
