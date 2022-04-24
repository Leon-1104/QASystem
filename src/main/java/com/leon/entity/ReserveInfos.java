package com.leon.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-17
 */
@Data
@TableName("reserve_infos")
@ApiModel(value = "ReserveInfos对象", description = "")
public class ReserveInfos implements Serializable {


    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    @Alias("预约信息id")
    @ApiModelProperty("id")
    private Integer id;
    @Alias("预约时间")
    @ApiModelProperty("预约时间")
    private LocalDateTime resTime;
    @Alias("教师姓名")
    @ApiModelProperty("教师姓名")
    private String teacherName;
    @Alias("办公室")
    @ApiModelProperty("办公室")
    private String office;
    @Alias("学生姓名")
    @ApiModelProperty("学生姓名")
    private String studentName;
    @Alias("课程姓名")
    @ApiModelProperty("课程姓名")
    private String courseName;
    @Alias("教师ID")
    @ApiModelProperty("老师id")
    private Integer teacherId;
    @Alias("学生ID")
    @ApiModelProperty("学生id")
    private Integer studentId;
    @Alias("课程ID")
    @ApiModelProperty("课程id")
    private Integer courseId;
    @Alias("创建请求时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建请求时间")
    private LocalDateTime createTime;
    @Alias("教师是否接受预约")
    private String acceptFlag;
    //备注
    @Alias("备注")
    private String remark;


}
