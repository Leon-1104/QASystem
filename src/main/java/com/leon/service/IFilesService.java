package com.leon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leon.entity.Files;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-23
 */
public interface IFilesService extends IService<Files> {
        IPage<Files> getPage(int currentPage, int PageSize, Files files);
}
