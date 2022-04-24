package com.leon.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.common.Constants;
import com.leon.common.Result;
import com.leon.entity.Teacher;
import com.leon.exception.ServiceException;
import com.leon.service.ICourseService;
import com.leon.service.ITeacherService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 教师信息表 前端控制器
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Resource
    private ITeacherService teacherService;
    @Resource
    private ICourseService courseService;

    @GetMapping
    public Result getAll() {
        return new Result(true, teacherService.list());
    }

    @PostMapping
    public Result save(@RequestBody Teacher teacher) {
        boolean flag = teacherService.save(teacher);
        return new Result(flag, flag ? "添加成功" : "添加失败");
    }

    @PutMapping("/{teacherId}/{password}")
    public Result updatePassword(@PathVariable Integer teacherId,@PathVariable String password) {
      return new Result(teacherService.updatePasswordById(teacherId,password));
    }

    @PutMapping
    public Result update(@RequestBody Teacher teacher) {
        return new Result(teacherService.updateById(teacher));
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {
        return new Result(teacherService.removeById(id));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        return new Result(teacherService.removeByIds(ids));
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id) {
        return new Result(true, teacherService.getById(id));
    }

    @GetMapping("{currentPage}/{pageSize}")
    public Result getPage(
            @PathVariable int currentPage,
            @PathVariable int pageSize,
            @RequestParam String teacherName,
            @RequestParam String type) {
        IPage<Teacher> page = teacherService.findPage(currentPage, pageSize, teacherName, type);
        //如果当前页码值大于了总页码值
        if (currentPage > page.getPages()) {
            page = teacherService.findPage((int) page.getPages(), pageSize, teacherName, type);
        }
        return new Result(true, page);
    }
    /**
     * 导出功能
     * @param response
     * @return
     * @throws Exception
     */
    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Teacher> list = teacherService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        List<Map<String, Object>> rows = list.stream().map(item -> {
            Map<String, Object> maps = new HashMap<>();
            maps.put("teacherId", item.getTeacherId().toString());
            maps.put("teacherName", item.getTeacherName());
//            maps.put("password", item.getPassword());
            maps.put("type",item.getType());
            maps.put("office",item.getOffice());
            maps.put("gender",item.getGender());
            maps.put("institute",item.getInstitute());
            maps.put("tel",item.getTel());
            maps.put("email",item.getEmail());
            maps.put("cardId", item.getCardId());
            return maps;
        }).collect(Collectors.toList());

        // Title
        int columns = Class.forName("com.leon.entity.Teacher").getDeclaredFields().length;
        excelWriter.merge(columns - 1, "教师信息");

        //自定义标题别名
        excelWriter.addHeaderAlias("teacherId", "教师编号");
        excelWriter.addHeaderAlias("teacherName", "教师姓名");
        excelWriter.addHeaderAlias("gender", "性别");
        excelWriter.addHeaderAlias("office","办公室");
        excelWriter.addHeaderAlias("institute", "学院");
        excelWriter.addHeaderAlias("type","职称");
        excelWriter.addHeaderAlias("tel", "电话");
        excelWriter.addHeaderAlias("email", "邮箱");
        excelWriter.addHeaderAlias("cardId", "身份证号");

        excelWriter.setColumnWidth(0, 20);
        excelWriter.setColumnWidth(1, 20);
        excelWriter.setColumnWidth(2, 20);
        excelWriter.setColumnWidth(3, 20);
        excelWriter.setColumnWidth(4, 20);
        excelWriter.setColumnWidth(5, 20);
        excelWriter.setColumnWidth(6, 20);
        excelWriter.setColumnWidth(7, 20);
        excelWriter.setColumnWidth(8, 20);
        excelWriter.write(rows, true);
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("教师信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        out.close();
        excelWriter.close();
    }

    @PostMapping("/import")
    public Result imp(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            ExcelReader excelReader = ExcelUtil.getReader(inputStream);
            List<Teacher> list = excelReader.readAll(Teacher.class);
            teacherService.saveBatch(list);
            return new Result(true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500, "文件上传错误");
        }
    }


}
