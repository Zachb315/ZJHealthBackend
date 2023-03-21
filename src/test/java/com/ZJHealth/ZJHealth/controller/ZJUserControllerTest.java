package com.ZJHealth.ZJHealth.controller;

import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import com.ZJHealth.ZJHealth.service.ZJUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ZJUserController.class)
class ZJUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ZJUserService zjUserService;

    @Mock
    private ZJUserRepository zjUserRepository;



    @Test
    void register() {
    }

    @Test
    void listall() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/list"))
                .andExpect(status().isOk());
    }
}