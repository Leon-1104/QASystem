package com.leon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leon.entity.Student;
import com.leon.mapper.StudentMapper;
import com.leon.service.IStudentService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 学生信息表 服务实现类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {


    @Resource
    private StudentMapper studentMapper;

    /**
     * 分页查询多条件搜索
     *
     * @param currentPage
     * @param PageSize
     * @param student
     * @return
     */
    @Override
    public IPage<Student> getPage(int currentPage, int PageSize, Student student) {
        /*
        模糊搜索
         */
        LambdaQueryWrapper<Student> lqw = new LambdaQueryWrapper<>();
        lqw.like(null != student.getStudentId(), Student::getStudentId, student.getStudentId());
        lqw.like(Strings.isNotEmpty(student.getStudentName()), Student::getStudentName, student.getStudentName());
        lqw.like(Strings.isNotEmpty(student.getMajor()), Student::getMajor, student.getMajor());
        IPage<Student> page = new Page<>(currentPage, PageSize);
        studentMapper.selectPage(page, lqw);
        return page;
    }

    @Override
    public IPage<Student> getPage(int currentPage, int PageSize) {
        IPage<Student> page = new Page<>(currentPage, PageSize);
        studentMapper.selectPage(page, null);
        return page;
    }

    @Override
    public Boolean updatePasswordById(Integer studentId, String password) {
        return studentMapper.updatePasswordById(studentId,password);
    }
}
