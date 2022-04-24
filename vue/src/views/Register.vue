<template>
  <div class="wrapper">
    <div
        style="margin: 200px auto; background-color: #fff; width: 350px; height: 400px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请再次输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">确定</el-button>
          <el-button type="warning" size="small" autocomplete="off">返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>
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
          this.request.post(this.user).then(resp => {

            if (resp.flag === false) {
              this.$message.error("用户名或密码错误")
            } else {
              switch (resp.data.role) {
                case "0"://管理员
                  this.$cookies.set("cname", resp.data.adminName);
                  this.$cookies.set("cid", resp.data.adminId);
                  this.$cookies.set("role", 0);
                  this.$router.push( '/');
                  break;
                case "1"://老师
                  this.$cookies.set("cname", resp.data.teacherName);
                  this.$cookies.set("cid", resp.data.teacherId);
                  this.$cookies.set("role", 1);
                  this.$router.push('/');
                  break;
                case "2"://学生
                  this.$cookies.set("cname", resp.data.studentName);
                  this.$cookies.set("cid", resp.data.studentId);
                  this.$cookies.set("role", 2);
                  this.$router.push('/');
                  break;
              }
              localStorage.setItem("user", JSON.stringify(resp.data));
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
