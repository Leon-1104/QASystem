package com.leon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.entity.ReserveInfos;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-17
 */
public interface IReserveInfosService extends IService<ReserveInfos> {


    IPage<ReserveInfos> stuFindPage(int currentPage, int pageSize, String teacherName, String courseName, Integer id);

    IPage<ReserveInfos> teacherFindPage(int currentPage, int pageSize, String studentName, String courseName, Integer id);

    IPage<ReserveInfos> getPage(int currentPage, int pageSize, ReserveInfos reserveInfos);
}
