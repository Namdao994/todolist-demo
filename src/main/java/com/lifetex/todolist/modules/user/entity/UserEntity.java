package com.lifetex.todolist.modules.user.entity;

import com.lifetex.todolist.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Email
    private String email;

}
