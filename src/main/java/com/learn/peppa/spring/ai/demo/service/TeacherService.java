package com.learn.peppa.spring.ai.demo.service;

import com.learn.peppa.spring.ai.demo.feign.TeacherFeign;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Resource
    private TeacherFeign teacherFeign;

    public String getTeacherProfileById(Integer id) {
        return teacherFeign.getTeacherProfileById(id).getData().getName();
    }
}
