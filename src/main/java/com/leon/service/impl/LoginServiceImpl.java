package com.leon.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leon.common.Constants;
import com.leon.entity.*;
import com.leon.exception.ServiceException;
import com.leon.mapper.LoginMapper;
import com.leon.mapper.RoleMapper;
import com.leon.mapper.RoleMenuMapper;
import com.leon.service.IMenuService;
import com.leon.service.LoginService;
import com.leon.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录服务类
 *
 * @author 赵号龙
 * @since 2022-03-14
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, UserDTO> implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    private static final Log LOG = Log.get();

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    IMenuService menuService;

    /**
     * 管理员登录
     *
     * @param username
     * @param password
     * @return 返回一个admin实体
     */
    @Override
    public Admin adminLogin(Integer username, String password) {

        try {
            if (null == username || StrUtil.isBlank(password)) {
                return null;
            } else {
                Admin admin = loginMapper.adminLogin(username, password);
                if (admin != null) {

                    String token = TokenUtils.genToken(admin.getAdminId().toString(), admin.getAdminName());
                    admin.setToken(token);
                    String role = admin.getRole();
                    List<Menu> roleMenuList = getRoleMenus(role);
                    admin.setMenus(roleMenuList);

                    return admin;
                } else {
                    return null;
                }

            }
        } catch (Exception e) {
            LOG.error(e);
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }

    }

    /**
     * Teacher登录
     *
     * @param username
     * @param password
     * @return 返回一个Teacher实体
     */
    @Override
    public Teacher teacherLogin(Integer username, String password) {
        try {
            if (null == username || StrUtil.isBlank(password)) {
                return null;
            } else {
                Teacher teacher = loginMapper.teacherLogin(username, password);
                if (teacher != null) {
                    String token = TokenUtils.genToken(teacher.getTeacherId().toString(), teacher.getTeacherName());
                    teacher.setToken(token);
                    String role = teacher.getRole();
                    List<Menu> roleMenuList = getRoleMenus(role);
                    teacher.setMenus(roleMenuList);
                    return teacher;
                } else {
                    return null;
                }

            }
        } catch (Exception e) {
            LOG.error(e);
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }

    }

    /**
     * Student登录
     *
     * @param username
     * @param password
     * @return 返回一个Student实体
     */
    @Override
    public Student studentLogin(Integer username, String password) {
        try {
            if (null == username || StrUtil.isBlank(password)) {
                return null;
            } else {
                Student student = loginMapper.studentLogin(username, password);
                if (student != null) {
                    String token = TokenUtils.genToken(student.getStudentId().toString(), student.getStudentName());
                    student.setToken(token);
                    String role = student.getRole();
                    List<Menu> roleMenuList = getRoleMenus(role);
                    student.setMenus(roleMenuList);
                    return student;
                } else {
                    return null;
                }

            }
        } catch (Exception e) {
            LOG.error(e);
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }

    }

    /**
     * 获取当前角色的菜单列表
     *
     * @param role 当前角色的类型
     * @return
     */
    private List<Menu> getRoleMenus(String role) {

        Integer roleId = roleMapper.selectByRole(role);
        //当前角色的菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //查出所有菜单
        List<Menu> menuList = menuService.findMenuList();
        //最终返回的菜单
        List<Menu> roleMenuList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menuIds.contains(menu.getId())) {
                roleMenuList.add(menu);
            }
            List<Menu> children = menu.getChildren();
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenuList;
    }
}
