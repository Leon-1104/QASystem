<template>
  <el-card style="width: 700px;margin: 0 auto" >
    <el-form :model="form" label-width="70px" size="small">

      <el-upload
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess">
        <img v-if="form.avatar" :src="form.avatar" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form-item label="用户名">
        <el-input  v-if="this.userRole==='0'"  v-model="form.adminName"  autocomplete="off"></el-input>
        <el-input  v-else  v-model="form.teacherName"  autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="form.gender" placeholder="请选择性别">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年级" v-if="this.userRole==='2'">
        <el-input v-model="form.grade" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="专业" v-if="this.userRole==='2'">
        <el-input v-model="form.major" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="班级" v-if="this.userRole==='2'">
        <el-input v-model="form.cls" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="学院" v-if="this.userRole==='2'||this.userRole==='1'">
        <el-input v-model="form.institute" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="职称" v-if="this.userRole==='1'">
        <el-input v-model="form.type" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.tel" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="身份证号">
        <el-input v-model="form.cardId" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveEdit()">确 定</el-button>
        <el-button @click="$router.push('/')">取 消</el-button>
      </el-form-item>
    </el-form>


  </el-card>
</template>

<script>
export default {
  name: "PersonInfo",
  data() {
    return {
      form: {},
      userId: this.$cookies.get('cid'),
      userRole: this.$cookies.get('role'),
      username:this.$cookies.get('cname'),
      path: '',
      user: localStorage.getItem("user") ? JSON.stringify("user") : {}
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
    async getUser() {
      return (await this.request.get(this.path+'/' + this.userId)).data
    },
    handleAvatarSuccess(res) {
      this.form.avatar = res
    },
    saveEdit() { //保存编辑
      this.request.put(this.path, this.form).then(resp => {

        if (resp.flag) {

          // 更新浏览器存储的用户信息
          this.getUser().then(res => {
            res.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user", JSON.stringify(res))
          })
          this.$message.success("保存成功");
          //触发父级更新User方法
          this.$emit("refreshUser")

        } else {
          this.$message.error("保存失败");
        }
      })
    }
  }
}
</script>

<style scoped>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>
