package com.leon.utils;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.leon.service.IAdminService;
import com.leon.service.IStudentService;
import com.leon.service.ITeacherService;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;



/**
 * @author Leon
 */
@Component
public class TokenUtils {
    private static IAdminService staticAdminService;
    private static IStudentService staticStudentService;
    private static ITeacherService staticTeacherService;
    @Resource
    private IAdminService adminService;
    @Resource
    private IStudentService studentService;
    @Resource
    private ITeacherService teacherService;

    public void setAdminService(IAdminService adminService) {
        staticAdminService = adminService;
    }

    public void setStudentService(IStudentService studentService) {
        staticStudentService = studentService;
    }

    public void setTeacherService(ITeacherService teacherService) {
        staticTeacherService = teacherService;
    }

    /**
     * 生成token
     *
     * @return
     */
    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }
//
//    /**
//     * 获取当前登录的用户信息
//     *
//     * @return user对象
//     */
//    public static Admin getCurrentr() {
//        try {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            String token = request.getHeader("token");
//            if (StrUtil.isNotBlank(token)) {
//                String userId = JWT.decode(token).getAudience().get(0);
//                return  roleSelect(userId);
//            }
//        } catch (Exception e) {
//            return null;
//        }
//        return null;
//    }
//
//    public static Admin roleSelect(String userId) {
//        int flag = 9999;
//        if (userId.matches("1000\\d")) {
//            flag = 0;
//        } else if (userId.matches("200\\d{2}")) {
//            flag = 1;
//
//        } else if (userId.matches("2018\\d{3}")) {
//            flag = 2;
//        }
//        switch (flag) {
//            case 0:
//                return staticAdminService.getById(Integer.valueOf(userId));
//            case 1:
//                return  staticTeacherService.getById(Integer.valueOf(userId));
//            case 2:
//               return staticStudentService.getById(Integer.valueOf(userId));
//        }
//        return null;
//    }
}
