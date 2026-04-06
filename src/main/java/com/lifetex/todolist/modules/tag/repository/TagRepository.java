package com.lifetex.todolist.modules.tag.repository;

import com.lifetex.todolist.modules.tag.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> findAllByUserId(Long userId);
    Optional<TagEntity> findByIdAndUserId(Long id, Long userId);
}
