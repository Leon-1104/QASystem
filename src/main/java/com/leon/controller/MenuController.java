package com.leon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.common.Constants;
import com.leon.common.Result;
import com.leon.entity.Dict;
import com.leon.entity.Menu;
import com.leon.mapper.DictMapper;
import com.leon.service.IMenuService;
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
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @Resource
    private DictMapper dictMapper;


    @GetMapping("/ids")
    public Result findAllIds(){
        return new Result(true,menuService.list().stream().map(Menu::getId));
    }

    @GetMapping
    public Result getAll() {
        return new Result(true, menuService.findMenuList());
    }

    @PostMapping
    public Result save(@RequestBody Menu menu) {
        boolean flag = menuService.save(menu);
        return new Result(flag, flag ? "添加成功" : "添加失败");
    }

    @PutMapping
    public Result update(@RequestBody Menu menu) {
        return new Result(menuService.updateById(menu));
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {

        return new Result(menuService.removeById(id));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        return new Result(menuService.removeByIds(ids));
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id) {
        return new Result(true, menuService.getById(id));
    }

    @GetMapping("{currentPage}/{pageSize}")
    public Result getPage(
            @PathVariable int currentPage,
            @PathVariable int pageSize,
            Menu menu) {
        IPage<Menu> page = menuService.getPage(currentPage, pageSize, menu);
        //如果当前页码值大于了总页码值
        if (currentPage > page.getPages()) {
            page = menuService.getPage((int) page.getPages(), pageSize, menu);
        }
        return new Result(true, page);
    }




    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constants.DICT_TYPE_ICON);
        return new Result(true,dictMapper.selectList(queryWrapper));

    }


}
