# 高校答疑预约系统
- author zhl
- since 2022-03-12
## 项目结构
- 后端：lastDemo/*
- 前端：lastDemo/vue
## 如何运行
### 环境 
导入到IDEA或者Eclipse中，请确保安装了相关环境
- JDK:1.8
- Maven：5.7.*
- NODE.js:>=6.9.0
- MySQL：5.7.*
### 后端
1. 找到配置文件
path:src/main/resources/application.yml
2. 找到SQL文件进行导入
path:sql/qarsystem.sql
3. 找到 src/main/java/com/leon/LastDemoApplication.java
4. 运行即可
### 前端
1. 在命令行窗口进入到.\lastDemo\vue下
首先输入 
```shell
npm install
```
如果报错，请输入以下指令
``` shell
npm install --force 
```
2. 待安装完成后输入 
``` shell
npm run serve
```
### 测试用例
#### 管理员
- loginNum:10001
- pwd:admin
#### 教师：
- loginNum:20001
- pwd:123
#### 学生:
- loginNum:2018001
- pwd:123
## 简介
该项目为本人毕业设计，仅供学习使用。

## 技术
- 前端：Vue+Element UI
- 后端：SpringBoot + MyBatisPlus

## 功能介绍

### 系统角色功能描述
- 管理员 
  - 管理系统数据（CURD）
  - 文章管理
  - 文件管理
  - 个人信息修改
  - 密码修改
  - 系统角色管理
  - 菜单分配
  - 数据统计界面设计
- 教师
  - 查看预约
  - 处理预约
  - 查看学生信息
  - 修改密码
  - 修改个人信息
- 学生
  - 答疑预约
  - 查看预约
  - 修改密码
  - 修改个人信息
  - 查看系统文章
