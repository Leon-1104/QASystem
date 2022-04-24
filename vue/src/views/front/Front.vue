<template>


  <div class="main">
    <div class="wrapper">
      <!--    头部-->
      <div style="display: flex;height: 61px;line-height: 61px;  background-color: #3F5EFB;" class="header">
        <div style="width: 600px; display: flex;padding-left: 30px">

          <div style="flex: 1;"> 欢迎来到高校预约答疑系统</div>

        </div>
        <div style="flex: 1;">
          <el-menu
              :default-active="activeIndex2"
              mode="horizontal"
              @select="handleSelect"
              text-color="white"
              active-text-color="#42b983"
              class="el-menu--horizontal"
              router>
            <el-menu-item index="/front/home">首页</el-menu-item>
            <el-menu-item index="/front/selectTeachers">答疑预约</el-menu-item>
            <el-menu-item index="/front/resInfos">我的预约</el-menu-item>
            <el-menu-item index="/front/article">文章列表</el-menu-item>
          </el-menu>
        </div>
        <div style="width: 200px;">
          <div v-if="!username" style="text-align: right;padding-right: 30px">
            <el-button @click="$router.push('/login')">登录</el-button>
          </div>
          <div v-else style="text-align: right">
            <el-dropdown style="width: 100px; cursor: pointer;margin-right: 80px;font-size: 18px;color: black">
              <div style="display: inline-block">
                <img :src="user.avatar" alt="" style="width: 30px;border-radius:50%; position: relative;
top: 5px;right: 5px;" @click="$router.push('/front/personInfo')">
                <span>{{ username }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
              </div>

              <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                  <span style="text-decoration: none;" @click="$router.push('/front/personInfo')">个人信息</span>
                </el-dropdown-item>
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                  <span style="text-decoration: none;" @click="$router.push('/front/updatePassword')">修改密码</span>
                </el-dropdown-item>
                <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                  <span style="text-decoration: none" @click="logout">退出</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </div>
      <div style="width: 1500px;margin: 0 auto;" class="content">
        <router-view/>
      </div>

      <div class="footer">
        <v-footer>
        </v-footer>
      </div>
    </div>

  </div>

</template>

<script>
import myFooter from "@/components/myFooter";

export default {
  name: "Front",
  components: {
    "v-footer": myFooter
  },
  created() {

    this.getUser();
  },
  data() {
    let name = this.$cookies.get('cname');
    return {
      activeIndex2: '1',
      username: name,
      userRole:this.$cookies.get('role'),
      userId:this.$cookies.get("cid"),
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  methods: {

    getUser() {
      this.request.get('/students/' + this.userId).then(resp => {
        this.user = resp.data
        resp.token = JSON.parse(localStorage.getItem("user")).token
        localStorage.setItem("user", JSON.stringify(resp))

      })
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    logout() {
      this.$store.commit('logout');

      this.$cookies.remove('cname');
      this.$message.success("退出成功");
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


.el-menu--horizontal {
  border: none;
  background-color: #3F5EFB
}

.el-menu-item {
  font-size: 20px;
  background-color: #3F5EFB !important;
  font-weight: bold;
  margin-left: 20px;
}

.el-menu-item:hover {
  outline: 0 !important;
  color: orange !important;
}

.content {
  flex: 1;
  padding-top: 20px;
  padding-bottom: 50px;
}

.wrapper {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  width: 100%;
  height: 100vh;
  overflow: scroll;
  background-color: #F5F7FC;
  background-image: url("../../assets/loginbg.png");
}

.header {
  flex: 0;
}


.footer {
  bottom: 0;
  flex: 0;
  position: relative;
  width: 100%;
}

</style>



