package com.learn.peppa.spring.ai.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherProfileBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 后台员工id
     */
    private Integer employeeId;
    /**
     * 账号id
     */
    private Integer accountId;
    /**
     * 编号
     */
    private String code;
    /**
     * 姓名
     */
    private String name;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别(0:男，1:女)
     */
    private Integer sex;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 手机号掩码
     */
    private String maskPhone;
    /**
     * 手机密文
     */
    private String phoneCode;
    /**
     * 工作性质(1：全职，2：兼职)
     */
    private Integer natureOfWork;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 学历 (1:小学，2:初级中学，3:高级中学，4:专科，5:本科，6:硕士研究生，7:博士研究生)
     */
    private Integer education;
    /**
     * 入职时间
     */
    private Date entryTime;
    /**
     * 毕业院校
     */
    private String graduateInstitutions;
    /**
     * 国家
     */
    private Integer nationalityCountryId;
    /**
     * 国籍
     */
    @Deprecated
    private Integer nationality;
    /**
     * 籍贯
     */
    private Integer nativePlace;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 状态(1:在职，0:离职)
     */
    private Integer status;
    /**
     * 试用期(保护期)开始日期
     */
    private Date probationStartDate;
    /**
     * 试用期(保护期)结束日期
     */
    private Date probationEndDate;
    /**
     * 年级组
     */
    private Integer gradeGroupType;
    /**
     * 教师上课位置
     */
    private Integer classLocation;
    /**
     * 岗位类型
     */
    private Integer jobType;
    /**
     * 教师上课位置
     */
    private Integer liveroomCenter;
    /**
     * 教授科目
     */
    private String professorRange;
    /**
     * 教师类型 1员工 2候选人
     */
    private Integer teacherType;

    /**
     * 业务线
     */
    private Integer businessLine;
}
