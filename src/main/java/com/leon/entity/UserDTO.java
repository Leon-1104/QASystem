package com.leon.entity;

import lombok.Data;

import java.util.List;

/**
 * 接收前端登录用户的请求数据实体类
 */

@Data
public class UserDTO {
    private Integer username;
    private String password;
    private List<Menu> menus;
    private String role;


}
