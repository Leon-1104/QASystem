package com.leon.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 课程信息表
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@Getter
@Setter
@ApiModel(value = "Course对象", description = "")
@TableName("course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程id")
    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;
    @Alias("课程名")
    @ApiModelProperty("课程名")
    private String courseName;
    @Alias("教师id")
    @ApiModelProperty("教师id")
    private Integer teacherId;
    @Alias("教师姓名")
    @ApiModelProperty("教师姓名")
    private String teacherName;

}
