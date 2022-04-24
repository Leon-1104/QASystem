package com.leon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
//    IPage<Course> findPage(Page<Course> coursePage, Course course);

    IPage<Course> findPage(IPage<Course> page, @Param("courseName") String courseName,@Param("teacherName") String teacherName);





}
