package com.leon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leon.entity.Article;
import com.leon.mapper.ArticleMapper;
import com.leon.service.IArticleService;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    ArticleMapper articleMapper;

    @Override
    public IPage<Article> getPage(int currentPage, int PageSize, Article article) {
        /*
        模糊搜索
         */
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotBlank(article.getName()), Article::getName, article.getName());
        IPage<Article> page = new Page<>(currentPage, PageSize);
        articleMapper.selectPage(page, lqw);
        return page;
    }
}
