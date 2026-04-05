package com.lifetex.todolist.common.exception;

import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {
    private final String code;

    public SystemException(String code, String message) {
        super(message);
        this.code = code;
    }
}