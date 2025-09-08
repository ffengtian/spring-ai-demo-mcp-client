package com.learn.peppa.spring.ai.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Spring AI Demo MCP Client 主应用类
 * 
 * 功能特性：
 * - 集成DeepSeek大模型
 * - 支持MCP客户端通信
 * - 提供REST API和SSE流式响应
 * - 运行在8081端口
 */
@SpringBootApplication
public class SpringAiDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringAiDemoApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Spring AI Demo MCP Client Application...");
        
        ConfigurableApplicationContext context = SpringApplication.run(SpringAiDemoApplication.class, args);
    }
}
