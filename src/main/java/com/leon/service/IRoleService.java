package com.leon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leon.entity.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-17
 */
public interface IRoleService extends IService<Role> {
        IPage<Role> getPage(int currentPage, int PageSize, Role role);

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
