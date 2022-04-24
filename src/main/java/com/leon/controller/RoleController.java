package com.leon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.common.Result;
import com.leon.entity.Role;
import com.leon.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private IRoleService roleService;

    @GetMapping
    public Result getAll() {
        return new Result(true, roleService.list());
    }

    @PostMapping
    public Result save(@RequestBody Role role) {
        boolean flag = roleService.save(role);
        return new Result(flag, flag ? "添加成功" : "添加失败");
    }


    @PutMapping
    public Result update(@RequestBody Role role) {
        boolean flag = roleService.updateById(role);
        return new Result(flag,flag?"修改成功":"修改失败");
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {
        boolean flag = roleService.removeById(id);
        return new Result(flag,flag?"删除成功":"删除失败");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        boolean flag = roleService.removeByIds(ids);
        return new Result(flag,flag?"删除成功":"删除失败");
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id) {
        return new Result(true, roleService.getById(id));
    }

    @GetMapping("{currentPage}/{pageSize}")
    public Result getPage(
            @PathVariable int currentPage,
            @PathVariable int pageSize, Role role) {
        IPage<Role> page = roleService.getPage(currentPage, pageSize, role);
        //如果当前页码值大于了总页码值
        if (currentPage > page.getPages()) {
            page = roleService.getPage((int) page.getPages(), pageSize, role);
        }
        return new Result(true, page);
    }


    /**
     * 绑定角色和菜单关系
     *
     * @param roleId  角色id
     * @param menuIds 菜单id数组
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {

        roleService.setRoleMenu(roleId, menuIds);

        return new Result(true);
    }
    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId) {
        return new Result(true, roleService.getRoleMenu(roleId));
    }
}
