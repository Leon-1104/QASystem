package com.leon.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leon.entity.ReserveInfos;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-17
 */
@Mapper
public interface  ReserveInfosMapper extends BaseMapper<ReserveInfos> {

    IPage<ReserveInfos> findPage(Page<ReserveInfos> page, @Param("teacherName") String teacherName, @Param("courseName") String courserName);

    IPage<ReserveInfos> stuFindPage(Page<ReserveInfos> reserveInfosPage, @Param("teacherName") String teacherName, @Param("courseName") String courseName,@Param("id") Integer id);

    IPage<ReserveInfos> teacherPage(Page<ReserveInfos> reserveInfosPage,@Param("studentName") String studentName, @Param("courseName") String courseName,@Param("id") Integer id);
}
