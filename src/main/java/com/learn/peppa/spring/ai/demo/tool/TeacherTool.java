package com.learn.peppa.spring.ai.demo.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class TeacherTool {

    @Tool(name = "getTeacherProfile", description = "根据教师ID查询教师详情")
    public String getTeacherProfile(@ToolParam(description = "教师Id") String teacherId) {
        return String.format("教师Id为%s的教师信息如下：.... tool test" , teacherId );
    }
}
