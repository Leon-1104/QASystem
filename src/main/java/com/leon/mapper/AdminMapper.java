package com.leon.mapper;

import com.leon.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    @Update("update qarsystem.admin t set t.password=#{password} where t.admin_id=#{adminId}")
    boolean updatePasswordById(Integer adminId, String password);
}
