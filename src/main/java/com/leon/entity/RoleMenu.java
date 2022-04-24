package com.leon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Leon
 */
@Data
@TableName("sys_role_menu")
public class RoleMenu {
    private Integer roleId;
    private Integer menuId;
}
