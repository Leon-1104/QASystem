package com.leon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leon.entity.Course;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
public interface ICourseService extends IService<Course> {
//
//    Course getById(Integer id);

//    IPage<Course> getPage(int currentPage, int pageSize, Course course);

    IPage<Course> findPage(int currentPage, int pageSize, String courseName,String teacherName);


}
