<template>
  <div>

    <!--    轮播图-->
    <div style="margin: 10px 0;">
      <el-carousel height="600px">
        <el-carousel-item v-for="item in imgs" :key="item">
          <img :src="item" style="width: 100%;height: 100%">
        </el-carousel-item>
      </el-carousel>
    </div>


    <!--    新闻区-->
    <div class="news">
      <news></news>
    </div>


<!--    页脚-->
  </div>


</template>

<script>
import News from "@/views/front/News";
import myFooter from "@/components/myFooter";

export default {
  name: "frontHome",
  components: {
    News,
    myFooter
  },
  created() {
    this.request.get('/courses/stulook').then(res => {
      console.log(res.data)
      // res.data.filter()
    })
  },
  data() {
    return {
      imgs: [
        'https://www.scse.uestc.edu.cn/images/2022xinxueqi.jpg',
        'https://www.scse.uestc.edu.cn/images/lianghui.jpg',
      ]
    }
  }
}
</script>

<style scoped>
.news {
  width: 1500px;
  margin: 0 auto;
}
</style>