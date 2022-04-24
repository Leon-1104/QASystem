package com.leon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leon.entity.Menu;
import com.leon.mapper.MenuMapper;
import com.leon.service.IMenuService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-17
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Resource
    MenuMapper menuMapper;

    @Override
    public IPage<Menu> getPage(int currentPage, int pageSize, Menu menu) {
           /*
        模糊搜索
         */
        LambdaQueryWrapper<Menu> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(menu.getName()), Menu::getName, menu.getName());
        IPage<Menu> page = new Page<>(currentPage, pageSize);
        menuMapper.selectPage(page, lqw);
        return page;
    }

    @Override
    public List<Menu> findMenuList() {
        //查询所有数据
        List<Menu> menuList = list();
        // 找出pid为null的一级菜单
        List<Menu> parentNodes = menuList.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (Menu menu : parentNodes) {
            //筛选所有数据中pid=parentId的二级菜单
            menu.setChildren(menuList.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }


        return parentNodes;

    }
}
