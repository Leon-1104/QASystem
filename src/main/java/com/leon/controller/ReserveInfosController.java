package com.leon.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.common.Result;
import com.leon.entity.Course;
import com.leon.entity.ReserveInfos;
import com.leon.entity.Student;
import com.leon.entity.Teacher;
import com.leon.service.ICourseService;
import com.leon.service.IReserveInfosService;
import com.leon.service.IStudentService;
import com.leon.service.ITeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/resInfos")
public class ReserveInfosController {
    @Resource
    private IReserveInfosService reserveInfosService;

    @Resource
    private ITeacherService teacherService;
    @Resource
    private ICourseService courseService;
    @Resource
    private IStudentService studentService;

    @GetMapping
    public Result getAll() {
        return new Result(true, reserveInfosService.list());
    }
//
//    @GetMapping("{currentPage}/{pageSize}/{id}")
//    private Result getStudentResPage( @PathVariable Integer id,@PathVariable int currentPage, @PathVariable int pageSize,){
//        List<ReserveInfos> list = reserveInfosService.list();
//        Student student = studentService.getById(id);
//        for (ReserveInfos item : list) {
//            if (item.getStudentId().equals(id)){
//                student.getReserveInfosList().add(item);
//            }
//        }
//
//        reserveInfosService.getPage(currentPage,pageSize,);
//
//    }
    @GetMapping("/teacher/{id}")
    private Result getTeacherResList(@PathVariable Integer id){
        List<ReserveInfos> list = reserveInfosService.list();
        Teacher teacher = teacherService.getById(id);
        for (ReserveInfos item : list) {
            if (item.getTeacherId().equals(id)){
                teacher.getReserveInfosList().add(item);
            }
        }
        return new Result(true,teacher.getReserveInfosList());
    }



    @PostMapping
    public Result save(@RequestBody ReserveInfos reserveInfos) {

        List<Course> courseList = courseService.list();
        List<Student> studentList = studentService.list();
        for (Course course : courseList) {
            if (reserveInfos.getTeacherName().equals(course.getTeacherName())
                    && reserveInfos.getCourseName().equals(course.getCourseName())) {
                reserveInfos.setCourseId(course.getCourseId());
                reserveInfos.setTeacherId(course.getTeacherId());
                break;
            }
        }
        for (Student student : studentList) {
            if (reserveInfos.getStudentName().equals(student.getStudentName())) {
                reserveInfos.setStudentId(student.getStudentId());
                return new Result(reserveInfosService.save(reserveInfos), "预约成功");
            }
        }
        return new Result(false, "预约失败");
    }


    @PutMapping
    public Result update(@RequestBody ReserveInfos reserveInfos) {
        boolean flag = reserveInfosService.updateById(reserveInfos);
        return new Result(flag,flag?"修改成功":"修改失败");
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {
        boolean flag = reserveInfosService.removeById(id);
        return new Result(flag,flag?"删除成功":"删除失败");
    }

    /**
     * 学生查看个人预约分页
     * @param currentPage
     * @param pageSize
     * @param teacherName
     * @param courseName
     * @param id
     * @return
     */
    @GetMapping("/s/{currentPage}/{pageSize}/{id}")
    public Result stuFindPage(  @PathVariable int currentPage,
                                @PathVariable int pageSize,
                                @RequestParam String teacherName,
                                @RequestParam String courseName,
                                @PathVariable Integer id){
        IPage<ReserveInfos> page = reserveInfosService.stuFindPage(currentPage, pageSize, teacherName, courseName,id);
        //如果当前页码值大于了总页码值
        if (currentPage > page.getPages()) {
            page = reserveInfosService.stuFindPage((int) page.getPages(), pageSize, teacherName, courseName,id);
        }
        return new Result(true, page);
    }
    /**
     * 教师查看个人预约分页
     * @param currentPage
     * @param pageSize
     * @param studentName
     * @param courseName
     * @param id
     * @return
     */
    @GetMapping("/t/{currentPage}/{pageSize}/{id}")
    public Result teacherFindPage(  @PathVariable int currentPage,
                                @PathVariable int pageSize,
                                @RequestParam String studentName,
                                @RequestParam String courseName,
                                @PathVariable Integer id){
        IPage<ReserveInfos> page = reserveInfosService.teacherFindPage(currentPage, pageSize, studentName, courseName,id);
        //如果当前页码值大于了总页码值
        if (currentPage > page.getPages()) {
            page = reserveInfosService.teacherFindPage((int) page.getPages(), pageSize, studentName, courseName,id);
        }
        return new Result(true, page);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        boolean flag = reserveInfosService.removeByIds(ids);
        return new Result(flag,flag?"删除成功":"删除失败");
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id) {
        return new Result(true, reserveInfosService.getById(id));
    }

    @GetMapping("{currentPage}/{pageSize}")
    public Result getPage(
            @PathVariable int currentPage,
            @PathVariable int pageSize,
            ReserveInfos reserveInfos) {
        IPage<ReserveInfos> page = reserveInfosService.getPage(currentPage, pageSize, reserveInfos);
        //如果当前页码值大于了总页码值
        if (currentPage > page.getPages()) {
            page = reserveInfosService.getPage((int) page.getPages(), pageSize, reserveInfos);
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
        List<ReserveInfos> list = reserveInfosService.list();
        // 通过工具类创建writer 写出到磁盘路径

        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        List<Map<String, Object>> rows = list.stream().map(item -> {
            Map<String, Object> maps = new HashMap<>();
            maps.put("id",item.getId());
            maps.put("teacherName",item.getTeacherName());
            maps.put("studentName",item.getStudentName());
            maps.put("courseName",item.getCourseName());
            maps.put("createTime",item.getCreateTime());
            maps.put("resTime",item.getResTime());
            maps.put("office",item.getOffice());
            maps.put("acceptFlag",item.getAcceptFlag());
            maps.put("remark",item.getRemark());
            maps.put("teacherId",item.getTeacherId().toString());
            maps.put("courseId", item.getCourseId().toString());
            maps.put("studentId",item.getStudentId());
            return maps;
        }).collect(Collectors.toList());

        // Title
        int columns = Class.forName("com.leon.entity.ReserveInfos").getDeclaredFields().length;
        excelWriter.merge(columns - 1, "预约信息");

        //自定义标题别名
        excelWriter.addHeaderAlias("id","预约信息ID");
        excelWriter.addHeaderAlias("teacherName", "教师姓名");
        excelWriter.addHeaderAlias("studentName","学生姓名");
        excelWriter.addHeaderAlias("courseName","课程信息");
        excelWriter.addHeaderAlias("createTime","创建请求时间");
        excelWriter.addHeaderAlias("resTime","预约时间");
        excelWriter.addHeaderAlias("office","办公室");
        excelWriter.addHeaderAlias("remark","备注");
        excelWriter.addHeaderAlias("acceptFlag","教师是否接受预约");
        excelWriter.addHeaderAlias("studentId","学生ID");
        excelWriter.addHeaderAlias("teacherId", "教师ID");
        excelWriter.addHeaderAlias("courseId","课程ID");
        excelWriter.setColumnWidth(0, 20);
        excelWriter.setColumnWidth(1, 20);
        excelWriter.setColumnWidth(2, 20);
        excelWriter.setColumnWidth(3, 20);
        excelWriter.setColumnWidth(4, 20);
        excelWriter.setColumnWidth(5, 20);
        excelWriter.setColumnWidth(6, 20);
        excelWriter.setColumnWidth(7, 20);
        excelWriter.setColumnWidth(8, 20);
        excelWriter.setColumnWidth(9, 20);
        excelWriter.setColumnWidth(10, 20);
        excelWriter.setColumnWidth(11, 20);
        excelWriter.write(rows, true);
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("预约信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        out.close();
        excelWriter.close();
    }

}
