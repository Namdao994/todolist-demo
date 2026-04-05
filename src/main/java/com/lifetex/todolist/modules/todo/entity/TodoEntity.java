package com.lifetex.todolist.modules.todo.entity;

import com.lifetex.todolist.common.BaseEntity;
import com.lifetex.todolist.modules.tag.entity.TagEntity;
import com.lifetex.todolist.modules.todo.enums.PriorityEnum;
import com.lifetex.todolist.modules.todo.enums.StatusEnum;
import com.lifetex.todolist.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "todos")
public class TodoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;

    @Column(nullable = false, name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "todos_tags",
            joinColumns = @JoinColumn(name = "todo_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagEntity> tags;

}
