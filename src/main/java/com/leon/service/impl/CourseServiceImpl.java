package com.leon.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leon.entity.Course;
import com.leon.mapper.CourseMapper;
import com.leon.service.ICourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Resource
    private CourseMapper courseMapper;

//    @Override
//    public Course getById(Integer id) {
//        return courseMapper.getByIdCourse(id);
//    }
//
//    @Override
//    public IPage<Course> getPage(int currentPage, int pageSize, Course course) {
//         /*
//        模糊搜索
//         */
//        LambdaQueryWrapper<Course> lqw = new LambdaQueryWrapper<>();
//        lqw.like(null != course.getCourseId(), Course::getCourseId, course.getCourseId());
//        lqw.like(Strings.isNotEmpty(course.getCourseName()), Course::getCourseName, course.getCourseName());
//        lqw.like(Strings.isNotEmpty(course.getTeacherName()), Course::getTeacherName, course.getTeacherName());
//        IPage<Course> page = new Page<>(currentPage, pageSize);
//        courseMapper.selectPage(page, lqw);
//        return page;
//    }

    @Override
    public IPage<Course> findPage(int currentPage, int pageSize, String courseName,String teacherName) {

        return courseMapper.findPage(new Page<Course>(currentPage, pageSize), courseName, teacherName );
    }



}


