<template>
  <div style="color:#666 ">

    <div style="margin: 10px 0">
      <div style="padding: 20px 0 ;border-bottom: 1px dashed #ccc">
        <div class="pd-10" style="font-size: 20px;color: #3F5EFB;cursor: pointer">{{ article.name }}</div>
        <div style="font-size: 13px;margin-top:10px;">
          <i class="el-icon-user"></i><span>{{ article.user }}</span>
          <i class="el-icon-time" style="margin-left: 10px"></i><span>{{ article.time }}</span>
        </div>
      </div>

    </div>
    <div>
      <mavon-editor
          class="md"
          :value="article.content"
          :subfield="false"
          :defaultOpen="'preview'"
          :toolbarsFlag='false'
          :editable="false"
          :scrollStyle="true"
          :ishljs="true"
      />
    </div>


  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Article",
  data() {
    return {
      article: {},
      user: localStorage.getItem("user") ? JSON.stringify("user") : {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      const id = this.$route.query.id
      //拼接字符串进行多条件查询
      this.request.get("/article/" + id).then(resp => {
        this.article = resp.data;

      })
    },

  }
}
</script>

<style scoped>
.headerBg {
  background: #eee !important;
}
</style>