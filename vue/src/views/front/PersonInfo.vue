<template>
  <el-card style="width: 700px;margin: 20px auto">
    <el-form :model="form" label-width="70px" size="small">
      <el-upload
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          style="text-align: center">
        <img v-if="form.avatar" :src="form.avatar" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form-item label="用户名">
        <el-input v-model="this.username"  disabled autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="form.gender" placeholder="请选择性别">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年级">
        <!--              <el-select v-model="form.grade" placeholder="请选择年级">-->
        <!--                <el-option label="2019" value="2019"></el-option>-->
        <!--                <el-option label="2020" value="2020"></el-option>-->
        <!--                <el-option label="2021" value="2021"></el-option>-->
        <!--              </el-select>-->
        <el-input v-model="form.grade" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="专业">
        <el-input v-model="form.major" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="班级">
        <el-input v-model="form.cls" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="学院">
        <el-input v-model="form.institute" autocomplete="off"></el-input>
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
    handleAvatarSuccess(res) {
      this.form.avatar = res
    },
    saveEdit() { //保存编辑
      this.request.put(this.path, this.form).then(resp => {

        if (resp.flag) {
          this.$message.success("保存成功");
        } else {
          this.$message.error("保存失败");
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
