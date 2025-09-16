package com.learn.peppa.spring.ai.demo.tool;

import com.learn.peppa.spring.ai.demo.feign.TeacherFeign;
import com.learn.peppa.spring.ai.demo.vo.TeacherProfileBaseInfoVo;
import com.peppa.common.response.Response;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class TeacherTool {

    @Resource
    private TeacherFeign teacherFeign;

    @Tool(name = "getTeacherProfile", description = "根据教师ID查询教师详情")
    public String getTeacherProfile(@ToolParam(description = "教师Id") String teacherId) {
        // 参数非空判定
        if (teacherId == null || teacherId.trim().isEmpty()) {
            return "教师ID不能为空";
        }

        try {
            Response<TeacherProfileBaseInfoVo> response = teacherFeign.getTeacherProfileById(Integer.valueOf(teacherId));
            
            // 响应非空判定
            if (response == null) {
                return "获取教师信息失败：响应为空";
            }
            
            // 数据非空判定
            if (response.getData() == null) {
                return String.format("未找到教师ID为%s的教师信息", teacherId);
            }
            
            TeacherProfileBaseInfoVo teacher = response.getData();
            
            // 格式化返回结果
            StringBuilder result = new StringBuilder();
            result.append("教师详细信息：\n");
            result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            result.append(String.format("教师ID：%s\n", teacher.getId()));
            result.append(String.format("姓名：%s\n", teacher.getName() != null ? teacher.getName() : "未设置"));
            result.append(String.format("昵称：%s\n", teacher.getNickname() != null ? teacher.getNickname() : "未设置"));
            result.append(String.format("编号：%s\n", teacher.getCode() != null ? teacher.getCode() : "未设置"));
            result.append(String.format("手机号：%s\n", teacher.getMaskPhone() != null ? teacher.getMaskPhone() : "未设置"));
            result.append(String.format("邮箱：%s\n", teacher.getEmail() != null ? teacher.getEmail() : "未设置"));
            result.append(String.format("性别：%s\n", getSexText(teacher.getSex())));
            result.append(String.format("工作性质：%s\n", getNatureOfWorkText(teacher.getNatureOfWork())));
            result.append(String.format("学历：%s\n", getEducationText(teacher.getEducation())));
            result.append(String.format("毕业院校：%s\n", teacher.getGraduateInstitutions() != null ? teacher.getGraduateInstitutions() : "未设置"));
            result.append(String.format("入职时间：%s\n", teacher.getEntryTime() != null ? teacher.getEntryTime().toString() : "未设置"));
            result.append(String.format("状态：%s\n", getStatusText(teacher.getStatus())));
            result.append(String.format("教授科目：%s\n", teacher.getProfessorRange() != null ? teacher.getProfessorRange() : "未设置"));
            result.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            
            return result.toString();
            
        } catch (NumberFormatException e) {
            return "教师ID格式错误，请输入有效的数字ID";
        } catch (Exception e) {
            return String.format("获取教师信息时发生错误：%s", e.getMessage());
        }
    }
    
    /**
     * 获取性别文本
     */
    private String getSexText(Integer sex) {
        if (sex == null) return "未设置";
        return sex == 0 ? "男" : sex == 1 ? "女" : "未知";
    }
    
    /**
     * 获取工作性质文本
     */
    private String getNatureOfWorkText(Integer natureOfWork) {
        if (natureOfWork == null) return "未设置";
        return natureOfWork == 1 ? "全职" : natureOfWork == 2 ? "兼职" : "未知";
    }
    
    /**
     * 获取学历文本
     */
    private String getEducationText(Integer education) {
        if (education == null) return "未设置";
        switch (education) {
            case 1: return "小学";
            case 2: return "初级中学";
            case 3: return "高级中学";
            case 4: return "专科";
            case 5: return "本科";
            case 6: return "硕士研究生";
            case 7: return "博士研究生";
            default: return "未知";
        }
    }
    
    /**
     * 获取状态文本
     */
    private String getStatusText(Integer status) {
        if (status == null) return "未设置";
        return status == 1 ? "在职" : status == 0 ? "离职" : "未知";
    }
}
