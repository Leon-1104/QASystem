package com.leon.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leon.common.Result;
import com.leon.entity.Student;
import com.leon.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
 * 学生信息表 前端控制器
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping
    public Result getAll() {
        return new Result(true, studentService.list());
    }
    @PutMapping("/{studentId}/{password}")
    public Result updatePassword(@PathVariable Integer studentId,@PathVariable String password) {
        return new Result(studentService.updatePasswordById(studentId,password));
    }
    /**
     * 新增
     *
     * @param student
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Student student) {
        boolean flag = studentService.save(student);
        return new Result(flag, flag ? "添加成功" : "添加失败");
    }

    /**
     * 更新信息
     *
     * @param student
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Student student) {
        return new Result(studentService.updateById(student));
    }

    /**
     * 单个删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {

        return new Result(studentService.removeById(id));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        return new Result(studentService.removeByIds(ids));
    }

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result getById(@PathVariable Integer id) {
        return new Result(true, studentService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param Student
     * @return
     */
    @GetMapping("{currentPage}/{pageSize}")
    public Result getPage(@PathVariable int currentPage, @PathVariable int pageSize, Student Student) {
        IPage<Student> page = studentService.getPage(currentPage, pageSize, Student);
        //如果当前页码值大于了总页码值
        if (currentPage > page.getPages()) {
            page = studentService.getPage((int) page.getPages(), pageSize, Student);
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
        List<Student> list = studentService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        List<Map<String, Object>> rows = list.stream().map(item -> {
            Map<String, Object> maps = new HashMap<>();
            maps.put("studentId", item.getStudentId().toString());
            maps.put("studentName", item.getStudentName());
//            maps.put("password", item.getPassword());
            maps.put("grade",item.getGrade());
            maps.put("major",item.getMajor());
            maps.put("cls",item.getCls());
            maps.put("gender",item.getGender());
            maps.put("institute",item.getInstitute());
            maps.put("tel",item.getTel());
            maps.put("email",item.getEmail());
            maps.put("cardId", item.getCardId());
            return maps;
        }).collect(Collectors.toList());

        // Title
        int columns = Class.forName("com.leon.entity.Student").getDeclaredFields().length;
        excelWriter.merge(columns - 1, "学生信息");

        //自定义标题别名
        excelWriter.addHeaderAlias("studentId", "学号");
        excelWriter.addHeaderAlias("studentName", "姓名");
        excelWriter.addHeaderAlias("gender", "性别");

        excelWriter.addHeaderAlias("grade", "年级");
        excelWriter.addHeaderAlias("major", "专业");
        excelWriter.addHeaderAlias("cls", "班级");
        excelWriter.addHeaderAlias("institute", "学院");
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
        excelWriter.setColumnWidth(9, 20);
        excelWriter.setColumnWidth(10, 20);
        excelWriter.write(rows, true);
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("学生信息", "UTF-8");
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
        List<Student> list = excelReader.readAll(Student.class);
        boolean flag = studentService.saveBatch(list);
        return new Result(flag, flag ? "批量添加成功" : "批量添加失败");
    }


}
