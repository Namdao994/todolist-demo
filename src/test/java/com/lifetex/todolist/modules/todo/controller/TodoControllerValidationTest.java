package com.lifetex.todolist.modules.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifetex.todolist.modules.todo.dto.TodoCreateRequest;
import com.lifetex.todolist.modules.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
class TodoControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    private final String URL_TEMPLATE = "/api/v1/todos";

    private TodoCreateRequest validRequest() {
        TodoCreateRequest req = new TodoCreateRequest();
        req.setTitle("Buy groceries");
        req.setDescription("Milk and eggs");
        req.setStatus("PENDING");
        req.setPriority("HIGH");
        req.setDueDate(LocalDateTime.now().plusDays(1));
        return req;
    }

    @Test
    void createTodo_shouldSuccess_whenValidRequest() throws Exception {
        TodoCreateRequest payload = validRequest();
        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload))
        )
                .andExpect(status().isCreated());

    }

    @Test
    void createTodo_shouldFail_whenBodyIsEmpty() throws Exception {
        mockMvc.perform(post(URL_TEMPLATE)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    //Test validation từng field
    //Title
    @Test
    void createTodo_shouldFail_whenTitleIsMissing() throws Exception {
        TodoCreateRequest payload = validRequest();
        payload.setTitle(null);

        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createTodo_shouldFail_whenTitleTooShort() throws Exception {
        TodoCreateRequest payload = validRequest();
        payload.setTitle("ab"); // < 3 ký tự

        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createTodo_shouldFail_whenTitleTooLong() throws Exception {
        TodoCreateRequest payload = validRequest();

        String longTitle = "a".repeat(51); // > 50 ký tự
        payload.setTitle(longTitle);

        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload))
        ).andExpect(status().isBadRequest());
    }
    //Description
    @Test
    void createTodo_shouldFail_whenDescriptionIsMissing() throws Exception {
        TodoCreateRequest request = validRequest();
        request.setDescription(null);

        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createTodo_shouldFail_whenDescriptionIsTooShort() throws Exception {
        TodoCreateRequest request = validRequest();
        request.setDescription("ab");

        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }
    //Status
    @Test
    void createTodo_shouldFail_whenStatusIsMissing() throws Exception {
        TodoCreateRequest request = validRequest();
        request.setStatus(null);

        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createTodo_shouldFail_whenStatusIsInvalid() throws Exception {
        TodoCreateRequest request = validRequest();
        request.setStatus("INVALID");

        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"PENDING", "IN_PROGRESS", "COMPLETED", "FAILED"})
    void createTodo_shouldSuccess_forAllValidStatus(String status) throws Exception {
        TodoCreateRequest request = validRequest();
        request.setStatus(status);

        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());
    }

    //Priority
    @Test
    void createTodo_shouldFail_whenPriorityIsInvalid() throws Exception {
        TodoCreateRequest request = validRequest();
        request.setPriority("INVALID");

        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"LOW", "MEDIUM", "HIGH"})
    void createTodo_shouldSuccess_forAllValidPriority(String status) throws Exception {
        TodoCreateRequest request = validRequest();
        request.setPriority(status);

        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());
    }

    //DueDate
    @Test
    void createTodo_shouldFail_whenDueDateIsMissing() throws Exception {
        TodoCreateRequest request = validRequest();
        request.setDueDate(null);
        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createTodo_shouldFail_whenDueDateIsInvalid() throws Exception {
        TodoCreateRequest request = validRequest();
        LocalDateTime dueDate = LocalDateTime.now().minusDays(1);
        request.setDueDate(dueDate);
        mockMvc.perform(post(URL_TEMPLATE)
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }
}