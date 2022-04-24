<template>
  <el-card style="width: 700px;margin: 20px auto">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="旧密码" prop="oldPass">
        <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="pass">
        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="请再次确认新密码" prop="checkPass">
        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>


  </el-card>
</template>

<script>
export default {
  name: "UpdatePassword",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    var validateOldPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error("请输入旧密码"))
      } else if (value !== this.form.password) {
        callback(new Error('原密码错误'))
      } else {
        callback();
      }
    }
    return {
      form: {},
      userId: this.$cookies.get('cid'),
      userRole: this.$cookies.get('role'),
      username: this.$cookies.get('cname'),
      path: '',
      user: localStorage.getItem("user") ? JSON.stringify("user") : {},
      ruleForm: {
        pass: '',
        checkPass: '',
        oldPass: ''
      },
      rules: {
        oldPass: [
          {validator: validateOldPass, trigger: 'blur'}
        ],
        pass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
      }

    }

  },
  created() {
    this.path = this.selectPathByRole();
    this.request.get(this.path + "/" + this.userId).then(resp => {
      if (resp.flag) {
        this.form = resp.data;
      }
    })
  },
  methods: {
    submitForm() {
      this.request.put(this.path +'/'+ this.userId +'/'+ this.ruleForm.pass).then(resp => {
        if (resp.flag) {
          this.$message.success("修改成功，请重新登录")
          this.$store.commit('logout');
        } else {
          this.$message.error("修改失败");
        }
      })


    },
    resetForm(formName) {
      this.ruleForm = {};
    },

    selectPathByRole() {
      let path1 = '';
      switch (this.userRole) {
        case "0"://管理员
          path1 = "/admins";
          break;
        case "1"://老师
          path1 = "/teachers";
          break;
        case "2"://学生
          path1 = "/students";
          break;
      }
      return path1;
    },
  }
}
</script>

<style scoped>

</style>
