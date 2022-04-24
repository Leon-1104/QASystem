package com.leon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-17
 */
public interface IMenuService extends IService<Menu> {

    IPage<Menu> getPage(int currentPage, int pageSize, Menu menu);
    List<Menu> findMenuList();
}
