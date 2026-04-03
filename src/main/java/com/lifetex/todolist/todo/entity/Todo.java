package com.lifetex.todolist.todo.entity;

import com.lifetex.todolist.common.BaseEntity;
import com.lifetex.todolist.tag.entity.Tag;
import com.lifetex.todolist.todo.enums.Priority;
import com.lifetex.todolist.todo.enums.Status;
import com.lifetex.todolist.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "todos")
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false, name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "todos_tags",
            joinColumns = @JoinColumn(name = "todo_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

}
