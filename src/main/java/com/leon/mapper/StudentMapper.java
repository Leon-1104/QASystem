package com.leon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leon.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 学生信息表 Mapper 接口
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Update("update qarsystem.student t set t.password=#{password} where t.student_id=#{studentId}")
    Boolean updatePasswordById(Integer studentId, String password);
}
