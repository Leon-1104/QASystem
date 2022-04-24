<template>
  <div class="wrapper">
    <div
        style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"  @keyup.enter.native="login"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>

import {setRoutes} from "@/router";

export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 9, message: '长度在 3 到 9 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    login() {
      //表单校验
      this.$refs['userForm'].validate((valid) => {
        if (valid) {//合法
          this.request.post("/login", this.user).then(resp => {
            console.log(resp)
            if (resp.flag === false) {
              this.$message.error("用户名或密码错误")
            } else {
              switch (resp.data.role) {
                case "0"://管理员
                  this.$router.push('/');
                  this.$cookies.set("cname", resp.data.adminName);
                  this.$cookies.set("cid", resp.data.adminId);
                  this.$cookies.set("role", 0);
                  this.$cookies.set("password",resp.data.password)
                  break;
                case "1"://老师
                  this.$router.push('/');
                  this.$cookies.set("cname", resp.data.teacherName);
                  this.$cookies.set("cid", resp.data.teacherId);
                  this.$cookies.set("password",resp.data.password)
                  this.$cookies.set("role", 1);

                  break;
                case "2"://学生
                  this.$router.push('/front/home');
                  this.$cookies.set("cname", resp.data.studentName);
                  this.$cookies.set("cid", resp.data.studentId);
                  this.$cookies.set("password",resp.data.password)
                  this.$cookies.set("role", 2);
                  break;
              }
              this.$cookies.set("cavatar", resp.data.avatar);
              localStorage.setItem("user", JSON.stringify(resp.data));
              localStorage.setItem("menus", JSON.stringify(resp.data.menus));
              setRoutes();
              //存储用户信息到浏览器
              this.$message.success("登录成功")

            }
          })
        } else {
          this.$message.error("用户名或密码长度不合法")
          return false;
        }
      })

    }
  }

}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
  overflow: hidden;
}
</style>
