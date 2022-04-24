package com.leon.controller;

import com.leon.common.Constants;
import com.leon.common.Result;
import com.leon.entity.Admin;
import com.leon.entity.Student;
import com.leon.entity.Teacher;
import com.leon.entity.UserDTO;
import com.leon.exception.ServiceException;
import com.leon.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;


    @PostMapping
    public Result login(@RequestBody UserDTO userDTO) {
        try {
            Integer username = userDTO.getUsername();
            String password = userDTO.getPassword();
            Admin admin = loginService.adminLogin(username, password);
            if (null != admin) {
                return new Result(true, admin);
            }
            Teacher teacher = loginService.teacherLogin(username, password);
            if (null != teacher) {
                return new Result(true, teacher);
            }
            Student student = loginService.studentLogin(username, password);
            if (null != student) {
                return new Result(true, student);
            }
            return new Result(false,"当前用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
            throw  new ServiceException(Constants.CODE_500,"系统错误");
        }
    }


}
