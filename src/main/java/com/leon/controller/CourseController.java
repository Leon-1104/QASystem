package com.leon.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.common.AuthAccess;
import com.leon.common.Result;
import com.leon.entity.Course;
import com.leon.entity.Teacher;
import com.leon.service.ICourseService;
import com.leon.service.ITeacherService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/courses")
public class CourseController {
    @Resource
    private ICourseService courseService;
    @Resource
    private ITeacherService teacherService;






    /**
     * 查询所有
     *
     * @return
     */

    @GetMapping
    public Result getAll() {
        return new Result(true, courseService.list());
    }

    @AuthAccess
    @GetMapping("/stulook")
    public Result stuFindAll() {
        return new Result(true, courseService.list());
    }

    /**
     * 新增
     *
     * @param course
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Course course) {

        List<Teacher> teacherList = teacherService.list();
        for (Teacher teacher : teacherList) {
            if (teacher.getTeacherName().equals(course.getTeacherName())) {
                course.setTeacherName(teacher.getTeacherName());
                course.setTeacherId(teacher.getTeacherId());
                return new Result(courseService.save(course), "添加成功");
            }

        }
        return new Result(false, "添加失败");
    }

    /**
     * 更新信息
     *
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Course course) {

        List<Teacher> teacherList = teacherService.list();
        for (Teacher teacher : teacherList) {
            if (teacher.getTeacherName().equals(course.getTeacherName())) {
                course.setTeacherName(teacher.getTeacherName());
                course.setTeacherId(teacher.getTeacherId());
                return new Result(courseService.updateById(course), "修改成功");
            }

        }

        return new Result(false, "修改失败");
    }

    /**
     * 单个删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {
        boolean flag = courseService.removeById(id);
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
        boolean flag = courseService.removeByIds(ids);
        return new Result(flag,flag?"删除成功":"删除失败");
    }

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id) {
        return new Result(true, courseService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("{currentPage}/{pageSize}")
    public Result getPage(@PathVariable int currentPage, @PathVariable int pageSize, @RequestParam String courseName, @RequestParam String teacherName) {


        IPage<Course> page = courseService.findPage(currentPage, pageSize, courseName, teacherName);
        //如果当前页码值大于了总页码值
        if (currentPage > page.getPages()) {
            page = courseService.findPage((int) page.getPages(), pageSize, courseName, teacherName);
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
        List<Course> list = courseService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");

        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        List<Map<String, Object>> rows = list.stream().map(item -> {
            Map<String, Object> maps = new HashMap<>();
            maps.put("courseId", item.getCourseId().toString());
            maps.put("courseName",item.getCourseName());
            maps.put("teacherId",item.getTeacherId().toString());
            maps.put("teacherName",item.getTeacherName());
            return maps;
        }).collect(Collectors.toList());

        // Title
        int columns = Class.forName("com.leon.entity.Course").getDeclaredFields().length;
        excelWriter.merge(columns - 1, "课程信息");

        //自定义标题别名
        excelWriter.addHeaderAlias("courseId","课程编号");
        excelWriter.addHeaderAlias("courseName","课程信息");
        excelWriter.addHeaderAlias("teacherId", "教师编号");
        excelWriter.addHeaderAlias("teacherName", "教师姓名");

        excelWriter.setColumnWidth(0, 20);
        excelWriter.setColumnWidth(1, 20);
        excelWriter.setColumnWidth(2, 20);
        excelWriter.setColumnWidth(3, 20);

        excelWriter.write(rows, true);
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("课程信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        out.close();
        excelWriter.close();
    }

    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        List<Course> list = excelReader.readAll(Course.class);
        courseService.saveBatch(list);
        return new Result(true);
    }

}
