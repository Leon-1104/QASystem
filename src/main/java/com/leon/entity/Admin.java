package com.leon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@Getter
@Setter
@ApiModel(value = "Admin对象", description = "")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableField(exist = false) //表示该属性不为数据库表字段，但又是必须使用的
    private String token;
    @ApiModelProperty("管理员id")
    @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;

    private String avatar;

    @ApiModelProperty("管理员姓名")
    private String adminName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("身份证")
    private String cardId;

    @ApiModelProperty("电话")
    private String tel;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("角色 0 管理员 1 老师 2 学生")
    private String role;

    @TableField(exist = false)
    private List<Menu> menus;

}
