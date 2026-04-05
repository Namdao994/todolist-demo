package com.lifetex.todolist.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class ApiError {
    private  String timestamp;
    private  String code;
    private  String message;
    private String traceId;

    public static ApiError of(String code, String message) {
        ApiError e = new ApiError();
        e.timestamp = Instant.now().toString();
        e.code = code;
        e.message = message;
        e.traceId = MDC.get("traceId");
        return e;
    }
}
