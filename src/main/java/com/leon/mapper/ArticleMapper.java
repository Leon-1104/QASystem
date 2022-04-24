package com.leon.mapper;

import com.leon.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-23
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
