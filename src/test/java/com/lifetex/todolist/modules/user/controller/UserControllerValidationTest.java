package com.lifetex.todolist.modules.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifetex.todolist.modules.user.dto.UserCreateRequest;
import com.lifetex.todolist.modules.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerValidationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private final String URL_TEMPLATE = "/api/v1/users";

    private UserCreateRequest validRequest() {
        UserCreateRequest req = new UserCreateRequest();
        req.setUsername("nam123");
        req.setEmail("nam@gmail.com");
        req.setPassword("123456");
        return req;
    }

    @Test
    void createUser_shouldSuccess_whenValidRequest() throws Exception {
        UserCreateRequest request = validRequest();
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());
    }

    //Username
    @Test
    void createUser_shouldFail_whenUsernameIsBlank() throws Exception {
        UserCreateRequest request = validRequest();
        request.setUsername("");

        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createUser_shouldFail_whenUsernameTooShort() throws Exception {
        UserCreateRequest request = validRequest();
        request.setUsername("ab");

        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createUser_shouldFail_whenUsernameTooLong() throws Exception {
        UserCreateRequest request = validRequest();
        request.setUsername("a".repeat(21));

        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    //Email
    @Test
    void createUser_shouldFail_whenEmailInvalid() throws Exception {
        UserCreateRequest request = validRequest();
        request.setEmail("abc123"); // sai format

        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createUser_shouldFail_whenEmailIsNull() throws Exception {
        UserCreateRequest request = validRequest();
        request.setEmail(null);

        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }
    //Password
    @Test
    void createUser_shouldFail_whenPasswordIsBlank() throws Exception {
        UserCreateRequest request = validRequest();
        request.setPassword("");

        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createUser_shouldFail_whenPasswordTooShort() throws Exception {
        UserCreateRequest request = validRequest();
        request.setPassword("123");

        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void createUser_shouldFail_whenPasswordTooLong() throws Exception {
        UserCreateRequest request = validRequest();
        request.setPassword("a".repeat(51));

        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }
}
