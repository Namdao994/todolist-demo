package com.lifetex.todolist.tag.entity;

import com.lifetex.todolist.common.BaseEntity;
import com.lifetex.todolist.tag.enums.Color;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "tags")
@Entity
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Color color;
}
