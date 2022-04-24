package com.leon.controller;

import cn.hutool.core.collection.CollUtil;
import com.leon.common.Result;
import com.leon.entity.Article;
import com.leon.entity.ReserveInfos;
import com.leon.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    IStudentService studentService;
    @Resource
    ITeacherService teacherService;
    @Resource
    IAdminService adminService;
    @Resource
    ICourseService courseService;
    @Resource
    IReserveInfosService reserveInfosService;
    @Resource
    IArticleService articleService;
    @Resource
    IFilesService filesService;

    @GetMapping("/example")
    public Result get() {

        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 155, 188, 90, 78, 178, 88));

        return new Result(true, map);
    }

    /**
     * 获取每天答疑人数
     *
     * @return
     */
    @GetMapping("/resinfos")
    public Result getResCount() {
        List<ReserveInfos> reserveInfosList = reserveInfosService.list();
        int Mon = 0;
        int Tue = 0;
        int Wed = 0;
        int Thu = 0;
        int Fri = 0;
        for (ReserveInfos item : reserveInfosList) {
            LocalDateTime date = item.getResTime();
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            switch (dayOfWeek) {
                case MONDAY:
                    Mon++;
                    break;
                case TUESDAY:
                    Tue++;
                    break;
                case WEDNESDAY:
                    Wed++;
                    break;
                case THURSDAY:
                    Thu++;
                    break;
                case FRIDAY:
                    Fri++;
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("周一", "周二", "周三", "周四", "周五"));
        map.put("y", CollUtil.newArrayList(Mon, Tue, Wed, Thu, Fri));

        return new Result(true, map);
    }

    /**
     * 获取系统人数
     *
     * @return
     */
    @GetMapping("/users")
    public Result getAllUsersToEcharts() {

        int adminCount = adminService.list().size();
        int studentsCount = studentService.list().size();
        int teacherCount = teacherService.list().size();
//        Map<String, Object> map = new HashMap<>();
//        map.put("x", CollUtil.newArrayList("管理员用户", "教师用户", "学生用户"));
        return new Result(true, CollUtil.newArrayList(adminCount, teacherCount, studentsCount));
    }

    @GetMapping("/articles")
    public Result getAllArticlesToEcharts(){
        List<Article> articleList = articleService.list();
        int annCount=0;
        int newsCount=0;
        for (Article article : articleList) {
            if (article.getType().equals("ANN")){
                annCount++;
            }else {
                newsCount++;
            }
        }
        return new Result(true,CollUtil.newArrayList(annCount,newsCount));
    }


}
