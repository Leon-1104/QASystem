package com.leon.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 学生信息表
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@Getter
@Setter
@ApiModel(value = "Student对象", description = "学生信息表")
@ToString

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Alias("学号")
    @ApiModelProperty("ID")
    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer studentId;
    @Alias("姓名")
    @ApiModelProperty("姓名")
    private String studentName;
    @Alias("密码")
    @ApiModelProperty("密码")
    private String password;
    @Alias("年级")
    @ApiModelProperty("年级")
    private String grade;
    @Alias("专业")
    @ApiModelProperty("专业")
    private String major;
    @Alias("班级")
    @ApiModelProperty("班级")
    private String cls;
    @Alias("学院")
    @ApiModelProperty("学院")
    private String institute;
    @Alias("性别")
    @ApiModelProperty("性别")
    private String gender;
    @Alias("电话")
    @ApiModelProperty("电话")
    private String tel;
    @Alias("邮箱")
    @ApiModelProperty("邮箱")
    private String email;
    @Alias("身份证号")
    @ApiModelProperty("身份证号")
    private String cardId;
    @Alias("角色")
    @ApiModelProperty("角色(0管理员，1教师，2学生)")
    private String role;
    @TableField(exist = false) //表示该属性不为数据库表字段，但又是必须使用的
    private String token;

    private String avatar;
//
//    @TableField(exist = false)
//    private List<ReserveInfos> reserveInfos;  //预约信息查看

    @TableField(exist = false)
    private List<Menu> menus;
    @TableField(exist = false)
    private List<ReserveInfos> reserveInfosList;

}
