package com.leon.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leon.entity.Menu;
import com.leon.entity.Role;
import com.leon.entity.RoleMenu;
import com.leon.mapper.RoleMapper;
import com.leon.mapper.RoleMenuMapper;
import com.leon.service.IMenuService;
import com.leon.service.IRoleService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Override
    public IPage<Role> getPage(int currentPage, int PageSize, Role role) {
        /*
        模糊搜索
         */
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(role.getName()), Role::getName, role.getName());
        IPage<Role> page = new Page<>(currentPage, PageSize);
        roleMapper.selectPage(page, lqw);
        return page;
    }

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
//        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("role_id",roleId);
//            roleMenuMapper.delete(queryWrapper);

        //先删除当前角色id的所有菜单绑定关系
        roleMenuMapper.deleteByRoleId(roleId);
        //再增
        List<Integer> menusCopy = CollUtil.newArrayList(menuIds);
        for (Integer menuId : menuIds) {
            Menu menu = menuService.getById(menuId);
            if (menu.getPid()!=null&& !menusCopy.contains(menu.getPid())){
                //如果选择的为二级菜单时 如果前端传递过来的menu数组中没有其父级Id 那么应该动态添加上父级id
                //否则将出现数据库中更新但前端未更新的情况
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menusCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {

    return roleMenuMapper.selectByRoleId(roleId);
    }
}
