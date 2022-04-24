package com.leon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leon.entity.Admin;
import com.leon.entity.Student;
import com.leon.entity.Teacher;
import com.leon.entity.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface LoginMapper extends BaseMapper<UserDTO> {

    @Select("select admin_id,admin_name,gender,tel,email,card_id,role from qarsystem.admin where admin_id = #{username} and password = #{password}")
    Admin adminLogin(Integer username, String password);

    @Select("select teacher_id,teacher_name,institute,gender,tel,email,card_id," +
            "type,role from qarsystem.teacher where teacher_id = #{username} and password = #{password}")
   Teacher teacherLogin(Integer username, String password);

    @Select("select student_id,student_name,grade,major,cls,institute,tel," +
            "email,card_id,gender,role from qarsystem.student where student_id = #{username} and password = #{password}")
    Student studentLogin(Integer username, String password);

} 
