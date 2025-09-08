package com.learn.peppa.spring.ai.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    private com.learn.peppa.spring.ai.demo.service.AiService aiService;

    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/ai/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("Spring AI Demo MCP Client"));
    }

    @Test
    void testInfoEndpoint() throws Exception {
        mockMvc.perform(get("/api/ai/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("Spring AI Demo MCP Client"))
                .andExpect(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.aiModel").value("DeepSeek Chat"));
    }

    @Test
    void testChatWithEmptyMessage() throws Exception {
        Map<String, String> request = Map.of("message", "");
        
        mockMvc.perform(post("/api/ai/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("消息内容不能为空"));
    }

    @Test
    void testChatStreamWithEmptyMessage() throws Exception {
        Map<String, String> request = Map.of("message", "");
        
        mockMvc.perform(post("/api/ai/chat/stream")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("data: {\"error\": \"消息内容不能为空\"}\n\n"));
    }
}
