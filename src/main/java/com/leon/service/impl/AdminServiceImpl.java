package com.leon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leon.entity.Admin;
import com.leon.mapper.AdminMapper;
import com.leon.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {


    @Resource
    private AdminMapper adminMapper;

    @Override
    public IPage<Admin> getPage(int currentPage, int pageSize, Admin admin) {

        LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
        IPage<Admin> page = new Page<>(currentPage, pageSize);
        adminMapper.selectPage(page, lqw);

        return page;
    }

    @Override
    public IPage<Admin> getPage(int currentPage, int pageSize) {
        IPage<Admin> page = new Page<>(currentPage, pageSize);
        adminMapper.selectPage(page, null);

        return page;
    }

    @Override
    public Boolean updatePasswordById(Integer adminId, String password) {
        return adminMapper.updatePasswordById(adminId, password);
    }

}
