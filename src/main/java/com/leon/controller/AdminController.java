package com.leon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.common.Result;
import com.leon.entity.Admin;
import com.leon.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @GetMapping
    public Result getAll() {
        return new Result(true, adminService.list());
    }
    @PutMapping("/{adminId}/{password}")
    public Result updatePassword(@PathVariable Integer adminId,@PathVariable String password) {
        return new Result(adminService.updatePasswordById(adminId,password));
    }
    @PostMapping
    public Result save(@RequestBody Admin admin) {
        boolean flag = adminService.save(admin);
        return new Result(flag, flag ? "添加成功" : "添加失败");
    }

    @PutMapping
    public Result update(@RequestBody Admin admin) {
        return new Result(adminService.updateById(admin));
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {
        return new Result(adminService.removeById(id));

    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id) {
        return new Result(true, adminService.getById(id));
    }

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    @GetMapping("{currentPage}/{pageSize}")
    public Result getPage(@PathVariable int currentPage, @PathVariable int pageSize, Admin Admin) {
        IPage<Admin> page = adminService.getPage(currentPage, pageSize, Admin);
        //如果当前页码值大于了总页码值
        if (currentPage > page.getPages()) {
            page = adminService.getPage((int) page.getPages(), pageSize, Admin);
        }
        return new Result(true, page);
    }


}
