package com.lifetex.todolist.modules.tag.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifetex.todolist.modules.tag.dto.TagCreateRequest;
import com.lifetex.todolist.modules.tag.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
@AutoConfigureMockMvc(addFilters = false)
class TagControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TagService tagService;

    private final String URL_TEMPLATE = "/api/v1/tags";

    private TagCreateRequest validRequest() {
        return new TagCreateRequest("Work", "BLUE");
    }

    // ===================== VALID =====================

    @Test
    void createTag_shouldSuccess_whenValidRequest() throws Exception {
        mockMvc.perform(post(URL_TEMPLATE).param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest())))
                .andExpect(status().isCreated());
    }

    // ===================== BODY EMPTY =====================

    @Test
    void createTag_shouldFail_whenBodyIsEmpty() throws Exception {
        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // ===================== NAME =====================

    @Test
    void createTag_shouldFail_whenNameIsNull() throws Exception {
        TagCreateRequest req = new TagCreateRequest(null, "BLUE");
        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTag_shouldFail_whenNameIsBlank() throws Exception {
        TagCreateRequest req = new TagCreateRequest("   ", "BLUE");
        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab", "a"})
    void createTag_shouldFail_whenNameIsTooShort(String shortName) throws Exception {
        TagCreateRequest req = new TagCreateRequest(shortName, "BLUE");
        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTag_shouldFail_whenNameIsTooLong() throws Exception {
        String longName = "a".repeat(51);
        TagCreateRequest req = new TagCreateRequest(longName, "BLUE");
        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    // ===================== COLOR =====================

    @Test
    void createTag_shouldFail_whenColorIsNull() throws Exception {
        TagCreateRequest req = new TagCreateRequest("Work", null);
        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"YELLOW", "PURPLE", "invalid", "blue", "red"})
    void createTag_shouldFail_whenColorIsInvalid(String invalidColor) throws Exception {
        TagCreateRequest req = new TagCreateRequest("Work", invalidColor);
        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"BLUE", "RED", "GREEN"})
    void createTag_shouldSuccess_whenColorIsValid(String validColor) throws Exception {
        TagCreateRequest req = new TagCreateRequest("Work", validColor);
        mockMvc.perform(post(URL_TEMPLATE).param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }
}