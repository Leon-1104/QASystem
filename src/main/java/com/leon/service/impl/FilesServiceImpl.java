package com.leon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leon.entity.Files;
import com.leon.mapper.FilesMapper;
import com.leon.service.IFilesService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-23
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {
    @Resource
    FilesMapper filesMapper;

    @Override
    public IPage<Files> getPage(int currentPage, int PageSize, Files files) {
        /*
        模糊搜索
         */
        LambdaQueryWrapper<Files> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotBlank(files.getName()), Files::getName, files.getName());
        lqw.eq(Files::getIsDelete,false);
        IPage<Files> page = new Page<>(currentPage, PageSize);
        filesMapper.selectPage(page, lqw);
        return page;
    }
}
