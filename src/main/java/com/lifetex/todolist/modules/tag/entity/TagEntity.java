package com.lifetex.todolist.modules.tag.entity;

import com.lifetex.todolist.common.BaseEntity;
import com.lifetex.todolist.modules.tag.enums.ColorEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "tags")
@Entity
public class TagEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ColorEnum color;
}
