package com.leon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leon.entity.Student;

/**
 * <p>
 * 学生信息表 服务类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
public interface IStudentService extends IService<Student> {
    IPage<Student> getPage(int currentPage, int pageSize, Student student);

    IPage<Student> getPage(int currentPage, int pageSize);

    Boolean updatePasswordById(Integer studentId, String password);
}
