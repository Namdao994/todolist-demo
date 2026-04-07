package com.lifetex.todolist.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifetex.todolist.common.ApiError;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String traceId = UUID.randomUUID().toString();
        Object ex = request.getAttribute("exception");
        String code = "UNAUTHORIZED";
        String message = "Access denied";
        if(ex instanceof ExpiredJwtException) {
            code = "JWT_EXPIRED";
            message = "JWT is expired";
        }
        MDC.put("traceId", traceId);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ApiError error = ApiError.of(code, message);
        response.getWriter().write(objectMapper.writeValueAsString(error));
        MDC.remove("traceId");
    }
}
