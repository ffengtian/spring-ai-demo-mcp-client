package com.learn.peppa.spring.ai.demo.config;

import com.learn.peppa.spring.ai.demo.tool.TeacherTool;
import org.springframework.ai.tool.StaticToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ToolConfig {

    @Bean
    public ToolCallbackProvider localToolCallbackProvider(TeacherTool teacherTool) {
        return MethodToolCallbackProvider.builder().toolObjects(teacherTool).build();
    }


    @Bean
    @Primary
    public ToolCallbackProvider allToolsProvider(
        @Qualifier("localToolCallbackProvider") ToolCallbackProvider localToolsProvider,
        @Qualifier("mcpAsyncToolCallbacks") ToolCallbackProvider mcpToolsProvider) {

        // 合并工具回调
        List<ToolCallback> allTools = new ArrayList<>();
        allTools.addAll(Arrays.asList(localToolsProvider.getToolCallbacks()));
        allTools.addAll(Arrays.asList(mcpToolsProvider.getToolCallbacks()));

        return new StaticToolCallbackProvider(allTools);
    }

}
