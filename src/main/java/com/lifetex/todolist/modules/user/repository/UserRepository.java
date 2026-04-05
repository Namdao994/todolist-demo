package com.lifetex.todolist.modules.user.repository;

import com.lifetex.todolist.modules.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
}
