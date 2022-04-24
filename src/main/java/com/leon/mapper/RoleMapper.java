package com.leon.mapper;

import com.leon.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-17
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select id from qarsystem.role where role.role = #{role}")
    Integer selectByRole(String role);
}
