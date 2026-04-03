package com.lifetex.todolist.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass //Đây không phải là bảng riêng trong DB Nhưng các class con kế thừa sẽ nhận các field này
public abstract class BaseEntity {

    @Column(updatable = false, nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist //Hàm này sẽ chạy trước khi INSERT vào DB
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate // Hàm này sẽ Chạy trước khi UPDATE
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}