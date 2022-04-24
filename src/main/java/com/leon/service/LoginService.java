package com.leon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leon.entity.Admin;
import com.leon.entity.Student;
import com.leon.entity.Teacher;
import com.leon.entity.UserDTO;

public interface LoginService extends IService<UserDTO> {

    Admin adminLogin(Integer username, String password);

    Teacher teacherLogin(Integer username, String password);

    Student studentLogin(Integer username, String password);
}
