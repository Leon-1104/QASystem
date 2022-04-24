<template>
  <div style="flex: 1;width: 100%;text-align: center" >
    <el-menu
        :default-active="activeIndex2"
        mode="horizontal"
        @select="handleSelect"
        text-color="black"
        active-text-color="#42b983"
        class="el-menu--horizontal"
        router>
      <el-menu-item index="/front2/home">首页</el-menu-item>
      <el-menu-item index="/front2/selectTeachers">答疑预约</el-menu-item>
      <el-menu-item index="/front2/personInfo">教师风采</el-menu-item>
      <el-menu-item index="/front2/article">文章列表</el-menu-item>
      <el-menu-item index="/front2/resInfos">我的预约</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "myHeader",
  data(){
    return{
      activeIndex2: '1',
    }
  },
  methods:{
    handleSelect(key,path){
      console.log(key,path)
    }
  }
}
</script>

<style scoped>

.el-menu--horizontal {
  border: none;
  background-color: #3F5EFB;
  text-align: center;
}
</style>