<template>
  <div style="line-height: 60px; display: flex">
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px" @click="collapse"></span>

      <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>

    </div>
    <el-dropdown style="width: 100px; cursor: pointer;">
      <div style="display: inline-block">
        <img :src="user.avatar" alt="" style="width: 30px;border-radius:50%; position: relative;
top: 5px;right: 5px;" @click="$router.push('/personInfo')">
        <span v-if="this.userRole==='0'">{{ user.adminName }}</span>

        <span v-else>{{ user.teacherName }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" @click="$router.push('/personInfo')">个人信息</span>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" @click="$router.push('/updatePassword')">修改密码</span>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Function,
    user: Object,
  },
  computed: {
    currentPathName() {
      return this.$store.state.currentPathName;　　//需要监听的数据
    }
  },
  watch: {
    currentPathName(newVal, oldVal) {
      console.log(newVal)
    }
  },
  data() {
    let name = this.$cookies.get('cname');
    return {
      username: name,
      // user: localStorage.getItem("user") ? JSON.stringify("user") : {}
      userRole: this.$cookies.get('role')
    }
  },
  methods: {
    logout() {
      this.$store.commit('logout');
      this.$cookies.remove('cname');
      this.$message.success("退出成功");
    }
  }
}
</script>

<style scoped>

</style>
