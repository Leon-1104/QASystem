package com.leon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leon.entity.Admin;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
public interface IAdminService extends IService<Admin> {
        IPage<Admin> getPage(int currentPage, int PageSize, Admin admin);
        IPage<Admin> getPage(int currentPage, int PageSize);

    Boolean updatePasswordById(Integer adminId, String password);
}
