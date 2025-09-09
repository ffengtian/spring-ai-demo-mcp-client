package com.learn.peppa.spring.ai.demo.controller;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import com.learn.peppa.spring.ai.demo.tool.TrafficHourTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * AI控制器
 * 提供REST API接口处理用户请求
 */
@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiController {

    private static final Logger logger = LoggerFactory.getLogger(AiController.class);

    @Resource
    private OpenAiChatModel openAiChatModel;

    @Resource
    private ToolCallbackProvider tools;

    @Autowired
    private TrafficHourTool trafficHourTool;

    @GetMapping("/test")
    public String test(@RequestParam("msg") String msg) {
        ChatClient chatClient =
            ChatClient.builder(openAiChatModel).defaultTools(trafficHourTool).defaultToolCallbacks(tools)
                .defaultAdvisors(SimpleLoggerAdvisor.builder().build(),
                    MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().build()).build()).build();
        return chatClient.prompt(msg).call().content();
    }
}
