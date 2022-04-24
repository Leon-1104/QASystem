package com.leon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leon.entity.Article;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-23
 */
public interface IArticleService extends IService<Article> {
        IPage<Article> getPage(int currentPage, int PageSize, Article article);
}
