package com.lifetex.todolist.modules.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifetex.todolist.common.PageResponse;
import com.lifetex.todolist.modules.tag.dto.TagResponse;
import com.lifetex.todolist.modules.todo.dto.TodoCreateRequest;
import com.lifetex.todolist.modules.todo.dto.TodoResponse;
import com.lifetex.todolist.modules.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lifetex.todolist.common.PageResponse;
import com.lifetex.todolist.common.exception.ResourceNotFoundException;
import com.lifetex.todolist.modules.tag.dto.TagCreateRequest;
import com.lifetex.todolist.modules.tag.dto.TagResponse;
import com.lifetex.todolist.modules.todo.dto.TodoUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    private final String URL = "/api/v1/todos";

    private TodoCreateRequest validRequest() {
        TodoCreateRequest req = new TodoCreateRequest();
        req.setTitle("Buy groceries");
        req.setDescription("Milk and eggs");
        req.setStatus("PENDING");
        req.setPriority("HIGH");
        req.setDueDate(LocalDateTime.now().plusDays(1));
        return req;
    }

    private TodoResponse sampleResponse() {
        return new TodoResponse(
                1L,
                "Buy groceries",
                "Milk and eggs",
                "PENDING",
                "HIGH",
                LocalDateTime.now().plusDays(1),
                Set.of()
        );
    }

    @Test
    void createTodo_shouldReturn201WithBody() throws Exception {

        when(todoService.createTodo(any(), eq(1L)))
                .thenReturn(sampleResponse());

        mockMvc.perform(post(URL)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest()))
                )
                .andExpect(status().isCreated())

                // check từng field
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Todo created successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Buy groceries"))
                .andExpect(jsonPath("$.data.description").value("Milk and eggs"))
                .andExpect(jsonPath("$.data.status").value("PENDING"))
                .andExpect(jsonPath("$.data.priority").value("HIGH"))

                // check tồn tại
                .andExpect(jsonPath("$.data.dueDate").exists())
                .andExpect(jsonPath("$.data.tags").isArray());
    }
    // ===================== GET ALL =====================

    @Test
    void getAllTodos_shouldReturn200WithPagedData() throws Exception {
        TodoResponse todo = sampleResponse();
        PageResponse<TodoResponse> page = new PageResponse<>(
                List.of(todo),  // content
                0,              // page
                10,             // size
                1L,             // totalElements
                1               // totalPages
        );

        when(todoService.getAllTodos(eq(1L), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(URL)
                        .param("userId", "1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content[0].id").value(1))
                .andExpect(jsonPath("$.data.content[0].title").value("Buy groceries"))
                .andExpect(jsonPath("$.data.totalElements").value(1))
                .andExpect(jsonPath("$.data.totalPages").value(1));
    }

// ===================== GET BY ID =====================

    @Test
    void getTodoById_shouldReturn200WhenFound() throws Exception {
        when(todoService.getTodoById(eq(1L), eq(1L))).thenReturn(sampleResponse());

        mockMvc.perform(get(URL + "/{id}", 1L)
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Buy groceries"));
    }

    @Test
    void getTodoById_shouldReturn404WhenNotFound() throws Exception {
        when(todoService.getTodoById(eq(99L), eq(1L)))
                .thenThrow(new ResourceNotFoundException("TODO_NOT_FOUND", "Todo not found"));

        mockMvc.perform(get(URL + "/{id}", 99L)
                        .param("userId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("TODO_NOT_FOUND"));
    }

// ===================== UPDATE =====================

    @Test
    void updateTodo_shouldReturn200WithUpdatedData() throws Exception {
        TodoUpdateRequest updateRequest = new TodoUpdateRequest();
        updateRequest.setTitle("Buy groceries updated");
        updateRequest.setDescription("Milk, eggs, and bread");
        updateRequest.setStatus("IN_PROGRESS");
        updateRequest.setPriority("MEDIUM");
        updateRequest.setDueDate(LocalDateTime.now().plusDays(2));

        TodoResponse updatedResponse = new TodoResponse(
                1L,
                "Buy groceries updated",
                "Milk, eggs, and bread",
                "IN_PROGRESS",
                "MEDIUM",
                LocalDateTime.now().plusDays(2),
                Set.of()
        );

        when(todoService.updateTodo(any(), eq(1L), eq(1L))).thenReturn(updatedResponse);

        mockMvc.perform(put(URL + "/{id}", 1L)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Buy groceries updated"))
                .andExpect(jsonPath("$.data.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.data.priority").value("MEDIUM"));
    }

    @Test
    void updateTodo_shouldReturn404WhenNotFound() throws Exception {
        TodoUpdateRequest updateRequest = new TodoUpdateRequest();
        updateRequest.setTitle("Ghost todo");

        when(todoService.updateTodo(any(), eq(99L), eq(1L)))
                .thenThrow(new ResourceNotFoundException("TODO_NOT_FOUND", "Todo not found"));

        mockMvc.perform(put(URL + "/{id}", 99L)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("TODO_NOT_FOUND"));
    }

// ===================== DELETE =====================

    @Test
    void deleteTodo_shouldReturn204WhenSuccess() throws Exception {
        doNothing().when(todoService).deleteTodo(eq(1L), eq(1L));

        mockMvc.perform(delete(URL + "/{id}", 1L)
                        .param("userId", "1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTodo_shouldReturn404WhenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("TODO_NOT_FOUND", "Todo not found"))
                .when(todoService).deleteTodo(eq(99L), eq(1L));

        mockMvc.perform(delete(URL + "/{id}", 99L)
                        .param("userId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("TODO_NOT_FOUND"));
    }

// ===================== TAG OPERATIONS =====================

    @Test
    void addTagToTodo_shouldReturn200WithTagAdded() throws Exception {
        TagResponse tagResponse = new TagResponse(10L, "urgent", "#FF0000");
        TodoResponse responseWithTag = new TodoResponse(
                1L, "Buy groceries", "Milk and eggs",
                "PENDING", "HIGH",
                LocalDateTime.now().plusDays(1),
                Set.of(tagResponse)
        );

        when(todoService.addTagToTodo(eq(1L), eq(10L), eq(1L))).thenReturn(responseWithTag);

        mockMvc.perform(post(URL + "/{id}/tags/{tagId}", 1L, 10L)
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.tags").isArray())
                .andExpect(jsonPath("$.data.tags[0].id").value(10))
                .andExpect(jsonPath("$.data.tags[0].name").value("urgent"));
    }

    @Test
    void addTagToTodo_shouldReturn404WhenTagNotFound() throws Exception {
        when(todoService.addTagToTodo(eq(1L), eq(99L), eq(1L)))
                .thenThrow(new ResourceNotFoundException("TAG_NOT_FOUND", "Tag not found"));

        mockMvc.perform(post(URL + "/{id}/tags/{tagId}", 1L, 99L)
                        .param("userId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("TAG_NOT_FOUND"));
    }

    @Test
    void removeTagFromTodo_shouldReturn200WithTagRemoved() throws Exception {
        TodoResponse responseWithoutTag = new TodoResponse(
                1L, "Buy groceries", "Milk and eggs",
                "PENDING", "HIGH",
                LocalDateTime.now().plusDays(1),
                Set.of()
        );

        when(todoService.removeTagFromTodo(eq(1L), eq(10L), eq(1L))).thenReturn(responseWithoutTag);

        mockMvc.perform(delete(URL + "/{id}/tags/{tagId}", 1L, 10L)
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.tags").isEmpty());
    }

    @Test
    void createAndAddTagToTodo_shouldReturn201WithNewTag() throws Exception {
        TagCreateRequest tagCreateRequest = new TagCreateRequest("updated", "RED");
        tagCreateRequest.setName("important");
        tagCreateRequest.setColor("#00FF00");

        TagResponse tagResponse = new TagResponse(20L, "important", "#00FF00");
        TodoResponse responseWithNewTag = new TodoResponse(
                1L, "Buy groceries", "Milk and eggs",
                "PENDING", "HIGH",
                LocalDateTime.now().plusDays(1),
                Set.of(tagResponse)
        );

        when(todoService.createAndAddTagToTodo(any(), eq(1L), eq(1L))).thenReturn(responseWithNewTag);

        mockMvc.perform(post(URL + "/{id}/tags", 1L)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tagCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.tags[0].name").value("important"))
                .andExpect(jsonPath("$.data.tags[0].color").value("#00FF00"));
    }
}
