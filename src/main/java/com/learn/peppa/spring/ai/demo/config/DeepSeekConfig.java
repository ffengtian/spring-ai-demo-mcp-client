package com.learn.peppa.spring.ai.demo.config;

import org.springframework.context.annotation.Configuration;

/**
 * DeepSeek AI配置类
 * DeepSeek使用OpenAI兼容的API接口
 * 使用Spring Boot自动配置
 */
@Configuration
public class DeepSeekConfig {
    // Spring Boot会自动配置OpenAI相关的Bean
    // 我们只需要在application.properties中配置相关参数即可
}
