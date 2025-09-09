package com.learn.peppa.spring.ai.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * AI控制器测试类
 */
@WebMvcTest(AiController.class)
class AiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testTestEndpoint() throws Exception {
        mockMvc.perform(get("/api/ai/test")
                .param("msg", "hello"))
                .andExpect(status().isOk());
    }

    @Test
    void testChatEndpoint() throws Exception {
        Map<String, String> request = Map.of("message", "hello");
        
        mockMvc.perform(post("/api/ai/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testToolsEndpoint() throws Exception {
        mockMvc.perform(get("/api/ai/tools"))
                .andExpect(status().isOk());
    }
}
