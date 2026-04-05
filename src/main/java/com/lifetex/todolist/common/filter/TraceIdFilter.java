package com.lifetex.todolist.common.filter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId); // lưu traceId vào MDC để log
        try {
            chain.doFilter(request, response); // tiếp tục chuỗi request
        } finally {
            MDC.remove("traceId"); // xóa traceId sau khi request xong
        }
    }
}
