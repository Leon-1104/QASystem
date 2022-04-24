package com.leon.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leon.entity.Teacher;
import com.leon.mapper.TeacherMapper;
import com.leon.service.ITeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 教师信息表 服务实现类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {


   @Resource
    private TeacherMapper teacherMapper;

    @Override
    public IPage<Teacher> findPage(int currentPage, int pageSize,  String teacherName, String type) {
       return teacherMapper.findPage(new Page<Teacher>(currentPage,pageSize),teacherName,type);
    }

    @Override
    public Boolean updatePasswordById(Integer teacherId,String password) {
        return  teacherMapper.updatePasswordById(teacherId,password);
    }

//    @Override
//    public IPage<Teacher> getPage(int currentPage, int PageSize, Teacher teacher) {
//        /*
//         模糊搜索
//          */
//
//
//        LambdaQueryWrapper<Teacher> lqw = new LambdaQueryWrapper<>();
//        lqw.like(null != teacher.getTeacherId(), Teacher::getTeacherId, teacher.getTeacherId());
//        lqw.like(Strings.isNotEmpty(teacher.getTeacherName()), Teacher::getTeacherName, teacher.getTeacherName());
//        lqw.like(Strings.isNotEmpty(teacher.getType()), Teacher::getType, teacher.getType());
//
//        IPage<Teacher> page = new Page<>(currentPage, PageSize);
//        teacherMapper.selectPage(page, lqw);
//
//        return page;
//    }
//
//    @Override
//    public IPage<Teacher> getPage(int currentPage, int PageSize) {
//        IPage<Teacher> page = new Page<>(currentPage, PageSize);
//        teacherMapper.selectPage(page, null);
//
//        return page;
//    }
}
