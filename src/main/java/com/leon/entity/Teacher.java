package com.leon.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * <p>
 * 教师信息表
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@Getter
@Setter
@ApiModel(value = "Teacher对象", description = "教师信息表")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableField(exist = false) //表示该属性不为数据库表字段，但又是必须使用的
    private String token;
    @Alias("ID")
    @ApiModelProperty("ID")
    @TableId(value = "teacher_id", type = IdType.AUTO)
    private Integer teacherId;
    @Alias("姓名")
    @ApiModelProperty("姓名")
    private String teacherName;
    @Alias("密码")
    @ApiModelProperty("密码")
    private String password;
    @Alias("学院")
    @ApiModelProperty("学院")
    private String institute;
    @Alias("性别")
    @ApiModelProperty("性别")
    private String gender;
    @Alias("电话号码")
    @ApiModelProperty("电话号码")
    private String tel;
    @Alias("邮箱")
    @ApiModelProperty("邮箱")
    private String email;
    @Alias("身份证号")
    @ApiModelProperty("身份证号")
    private String cardId;
    @Alias("职称")
    @ApiModelProperty("职称")
    private String type;
    @Alias("办公室")
    @ApiModelProperty("办公室")
    private String office;
    @ApiModelProperty("角色（0管理员，1教师，2学生）")
    private String role;

    private String avatar;
    @TableField(exist = false)
    private List<Course> courses;  //教授课程
    @TableField(exist = false)
    private List<Menu> menus;
    @TableField(exist = false)
    private List<LocalDateTime> spareTimes;  //空闲时间集合
    @TableField(exist = false)
    private List<ReserveInfos> reserveInfosList;  //预约信息查看处理
}
