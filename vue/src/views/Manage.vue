<template>
  <el-container style="min-height: 100vh">
    <el-aside :width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
      <Aside :is-collapse="isCollapse" :logo-text-show="logoTextShow" />
    </el-aside>
    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; ">
        <Header :collapse="collapse" :collapse-btn-class="collapseBtnClass" :user="this.user"/>
      </el-header>
      <el-main>
        <router-view @refreshUser="getUser"/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>


import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'Manage',
  components: {
    Aside,
    Header

  },
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,//侧边栏是否收缩
      sideWidth: 200,
      logoTextShow: true,//侧边栏名称是否显示
      isRouterAlive:true,
      userId:this.$cookies.get("cid"),
      user:{},
      userRole:this.$cookies.get("role"),
      path:''
    }
  },
  created() {

    this.path=this.selectPathByRole()
    this.getUser()
  },
  methods: {
     getUser() {
       this.request.get(this.path+'/' + this.userId).then(resp=>{
         this.user=resp.data
         console.log(this.user)
       })
    }, selectPathByRole() {
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
    collapse() {  // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {  // 收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {   // 展开
        this.sideWidth = 200;
        this.collapseBtnClass = 'el-icon-s-fold';
        this.logoTextShow = true;
      }
    }
  }
}
</script>

<style>

</style>
