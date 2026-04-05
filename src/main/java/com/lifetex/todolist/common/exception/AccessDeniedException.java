package com.lifetex.todolist.common.exception;

import lombok.Getter;

@Getter
public class AccessDeniedException extends RuntimeException {
    private final String code;

    public AccessDeniedException(String code, String message) {
        super(message);
        this.code = code;
    }
}