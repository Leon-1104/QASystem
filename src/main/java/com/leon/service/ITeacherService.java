package com.leon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leon.entity.Teacher;

/**
 * <p>
 * 教师信息表 服务类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
public interface ITeacherService extends IService<Teacher> {
//        IPage<Teacher> getPage(int currentPage, int PageSize, Teacher teacher);
//
//        IPage<Teacher> getPage(int currentPage, int PageSize);

    IPage<Teacher> findPage(int currentPage, int pageSize,  String teacherName, String type);

    Boolean updatePasswordById(Integer id,String password);
}
