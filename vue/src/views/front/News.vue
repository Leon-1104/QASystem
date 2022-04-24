<template>
  <div>

    <div class="news">
      <el-card>
        <div class="news1">
          <div class="newsHeader">
            <div><h2 style="color: #3F5EFB">综合新闻</h2></div>
            <div style="margin-left: 5px;color:#cccccc;">NEWS</div>
            <div><span class="more" style="cursor:pointer" @click="$router.push('/front/article')">更多>>></span></div>
          </div>
          <ul v-for="(item,index) in tableData" class="newsContent">
            <li v-if="index<8 & item.type==='NEWS'">
              <div class="text" @click="$router.push('/front/articleDetail?id='+item.id)">{{ item.name }}</div>
              <div>{{ item.time }}</div>
            </li>
          </ul>
        </div>
      </el-card>
      <el-card style="margin-left: 20px">
        <div class="news1" >
          <div class="newsHeader">
            <div><h2 style="color: #3F5EFB">最新公告</h2></div>
            <div style="margin-left: 5px;color:#cccccc;">ANNOUNCEMENT</div>
            <div><span class="more" style="cursor:pointer" @click="$router.push('/front/article')">更多>>></span></div>
          </div>
          <ul v-for="(item,index) in tableData" class="newsContent">
            <li v-if="index<8 & item.type==='ANN'">
              <div class="text" @click="$router.push('/front/articleDetail?id='+item.id)">{{ item.name }}</div>
              <div>{{ item.time }}</div>
            </li>
          </ul>
        </div>
      </el-card>
    </div>


  </div>
</template>

<script>
export default {
  name: 'News',
  data() {
    return {
      tableData: []
    }
  },
  created() {
    this.load();
  },
  methods: {

    load() {
      //拼接字符串进行多条件查询
      this.request.get("/article").then(resp => {


        resp.data.forEach(item => {

          item.time = this.formatDateTime(item.time);
          this.tableData.push(item)

        })


      })
    }, formatDateTime(inputTime) {//处理后端发来的时间数据
      if (!inputTime) {
        return '';
      }
      var time = inputTime.substr(0, 10)

      return time;
    },

  }
}
</script>

<style>
.news1 {
  margin-left: 20px;
  width: 680px;
}

.news {
  width: 100%;
  display: flex;

}

li div:last-child {
  float: right;
}

li div {
  display: inline-block;
}

.newsHeader {
  width: 700px;
  height: 30px;
  line-height: 30px;
  border-bottom: #cccccc 1px solid;
}

.newsHeader > div {
  float: left;

}

.newsHeader > div:last-child {
  float: right;
  color: #cccccc;
  margin-right: 5px;
}

.newsHeader > div:first-child {
  border-bottom: #3F5EFB 2px solid;
}

.newsContent {
  font-size: 15px;
  margin-top: 20px;

}

.newsContent {

}

.text {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  width: 300px;
  cursor: pointer
}

.text:hover {
  color: darkblue;
}

.more:hover {
  color: black;
}

</style>

