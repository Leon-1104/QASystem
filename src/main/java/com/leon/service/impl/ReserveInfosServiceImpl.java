package com.leon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leon.entity.ReserveInfos;
import com.leon.mapper.ReserveInfosMapper;
import com.leon.service.IReserveInfosService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-17
 */
@Service
public class ReserveInfosServiceImpl extends ServiceImpl<ReserveInfosMapper, ReserveInfos> implements IReserveInfosService {
    @Resource
    ReserveInfosMapper reserveInfosMapper;


    @Override
    public IPage<ReserveInfos> stuFindPage(int currentPage, int pageSize, String teacherName, String courseName, Integer id) {
        return reserveInfosMapper.stuFindPage(new Page<ReserveInfos>(currentPage, pageSize), teacherName, courseName, id);
    }

    @Override
    public IPage<ReserveInfos> teacherFindPage(int currentPage, int pageSize, String studentName, String courseName, Integer id) {
        return reserveInfosMapper.teacherPage(new Page<ReserveInfos>(currentPage, pageSize), studentName, courseName, id);
    }

    @Override
    public IPage<ReserveInfos> getPage(int currentPage, int pageSize, ReserveInfos reserveInfos) {
        LambdaQueryWrapper<ReserveInfos> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(reserveInfos.getTeacherName()), ReserveInfos::getTeacherName, reserveInfos.getTeacherName());
        lqw.like(Strings.isNotEmpty(reserveInfos.getStudentName()), ReserveInfos::getStudentName, reserveInfos.getStudentName());
        lqw.like(Strings.isNotEmpty(reserveInfos.getCourseName()), ReserveInfos::getCourseName, reserveInfos.getCourseName());
        IPage<ReserveInfos> page = new Page<>(currentPage, pageSize);
        reserveInfosMapper.selectPage(page, lqw);
        return page;
    }


}
