package com.leon.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.common.Result;
import com.leon.entity.Article;
import com.leon.service.IArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-23
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private IArticleService articleService;

    @GetMapping
    public Result getAll() {
        return new Result(true, articleService.list());
    }

    @PostMapping
    public Result save(@RequestBody Article article) {
        if (article.getId() == null) {
            article.setTime(DateUtil.now());
        }
        boolean flag = articleService.save(article);
        return new Result(flag, flag ? "添加成功" : "添加失败");
    }

    @PutMapping
    public Result update(@RequestBody Article article) {
        boolean flag = articleService.updateById(article);
        return new Result(flag,flag?"修改成功":"修改失败");
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {
        boolean flag = articleService.removeById(id);
        return new Result(flag,flag?"修改成功":"修改失败");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        boolean flag = articleService.removeByIds(ids);
        return new Result(flag,flag?"修改成功":"修改失败");
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id) {
        return new Result(true, articleService.getById(id));
    }

    @GetMapping("{currentPage}/{pageSize}")
    public Result getPage(
            @PathVariable int currentPage,
            @PathVariable int pageSize, Article article) {
        IPage<Article> page = articleService.getPage(currentPage, pageSize, article);
        //如果当前页码值大于了总页码值
        if (currentPage > page.getPages()) {
            page = articleService.getPage((int) page.getPages(), pageSize, article);
        }
        return new Result(true, page);
    }
}
