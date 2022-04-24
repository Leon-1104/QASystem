package com.leon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leon.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 教师信息表 Mapper 接口
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

//    List<Course> getCoursesCourseList();

    IPage<Teacher> findPage(Page<Teacher> Page,  @Param("teacherName") String teacherName, @Param("type")String type);

    @Update("update qarsystem.teacher t set t.password=#{password} where t.teacher_id=#{teacherId}")
    Boolean updatePasswordById(@Param("teacherId") Integer teacherId,@Param("password") String password);
}
