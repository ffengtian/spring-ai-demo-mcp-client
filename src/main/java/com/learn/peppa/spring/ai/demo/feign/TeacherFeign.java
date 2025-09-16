package com.learn.peppa.spring.ai.demo.feign;

import com.learn.peppa.spring.ai.demo.vo.TeacherProfileBaseInfoVo;
import com.peppa.common.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "PEPPA-TEACH-SERVER")
public interface TeacherFeign {

    @GetMapping("TeacherProfileBaseService/getTeacherProfileById")
    Response<TeacherProfileBaseInfoVo> getTeacherProfileById(@RequestParam(value = "id") Integer id);
}
