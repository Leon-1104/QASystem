<template>
  <div style="color:#666 ">
    <div style="margin: 10px 0">
      <el-input style="width: 300px" v-model="pagination.name" placeholder="请输入文章名"
                suffix-icon="el-icon-message" class="ml-5" size="small" @keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load" size="small">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reSet" size="small">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <div style="padding: 20px 0 ;border-bottom: 1px dashed #ccc" v-for="item in tableData" :key="item.id">
        <div class="pd-10" style="font-size: 20px;color: #3F5EFB;cursor: pointer" @click="$router.push('/front/articleDetail?id='+item.id)">{{ item.name }}</div>
        <div style="font-size: 13px;margin-top:10px;">
          <i class="el-icon-user"></i><span>{{ item.user }}</span>
          <i class="el-icon-time" style="margin-left: 10px"></i><span>{{ item.time }}</span>
        </div>
      </div>

    </div>


    <el-dialog title="文章信息" :visible.sync="viewDialogVis" width="60%">
      <el-card>
        <div>
          <mavon-editor
              class="md"
              :value="content"
              :subfield="false"
              :defaultOpen="'preview'"
              :toolbarsFlag='false'
              :editable="false"
              :scrollStyle="true"
              :ishljs="true"
          />
        </div>
      </el-card>
    </el-dialog>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagination.currentPage"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pagination.pageSize"
          layout="total,prev, pager, next"
          :total="pagination.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Article",
  data() {
    return {
      pickerOptions: {//设置禁用日期和时间段
        disabledDate: time => {//禁用休息日和下班时间
          return time.getTime() < Date.now();
        },
      },
      teachers: [],
      tableData: [],
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,//侧边栏是否收缩
      sideWidth: 200,
      logoTextShow: true,//侧边栏名称是否显示
      dialogFormVisible: false,
      dialogFormVisibleEdit: false,
      form: {},//表单数据
      pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 10,//每页显示的记录数
        total: 0,//总记录数
        name: ''
      },
      content: '',
      viewDialogVis: false,
      user: localStorage.getItem("user") ? JSON.stringify("user") : {}
    }
  },
  created() {
    this.load()
  },
  methods: {

    viewArticle(content) {
      this.content = content;
      this.viewDialogVis = true;
    },
    imgAdd(pos, $file) {
      let $vm = this.$refs.md;
      const formData = new FormData();
      formData.append('file', $file);
      axios({
        url: 'http://localhost:9090/files/upload',
        method: 'post',
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'},
      }).then(res => {
        $vm.$img2Url(pos, res.data);
      })

    },

    headerBg() {
      return 'background:#eee !important ;text-align: center';

    }
    ,
    cellStyle() {
      return '  text-align: center;font-size: 13px;'
    },
    load() {
      //拼接字符串进行多条件查询

      let param = "?name=" + this.pagination.name;
      this.request.get("/article/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param).then(resp => {
        this.pagination.total = resp.data.total;
        this.pagination.currentPage = resp.data.current;
        this.pagination.pageSize = resp.data.size;
        this.tableData = resp.data.records;



        // // 去重
        // let tempData2 =resp.data.records;
        // let hash = {};
        // tempData2 = tempData2.reduce(function (item, next) {
        //   hash[next.teacherName] ? '' : hash[next.teacherName] = true && item.push(next);
        //   return item;
        // }, []);
        // this.teachers = tempData2;


      })
    },
    exp() {
      window.open("http://localhost:9090/article/export");
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功");
      this.load();
    },
    handleSave() {
      this.dialogFormVisible = true;
      this.form = {};
      this.form.user = this.$cookies.get('cname');
    },
    save() {//新增函数
      this.request.post("/article", this.form).then(resp => {

        if (resp.flag) {
          this.dialogFormVisible = false;
          this.$message.success(resp.msg);
        } else {
          this.$message.error(resp.msg);
        }
      }).finally(() => {
        this.load();
      });
    },
    handleEdit(row) {//弹出编辑窗口
      this.dialogFormVisibleEdit = true;
      this.request.get("/article/" + row.id).then(resp => {
        if (resp.flag && resp.data != null) {
          this.form = resp.data;
        } else {
          this.$message.error(res.msg)
        }
      }).finally(() => {
        this.load();
      });
    },
    saveEdit() { //保存编辑
      this.request.put("/article", this.form).then(resp => {

        if (resp.flag) {
          this.dialogFormVisibleEdit = false;
          this.$message.success("保存成功！");
        } else {
          this.$message.error("保存失败");
        }
      }).finally(() => {
        this.load();
      });
    },
    reSet() {//重置搜索框
      this.pagination.name = "";

      this.load();
    },
    handleDelete(row) {//删除
      this.$confirm("此操作将永久删除当前信息，是否继续？", "提示", {type: "info"}).then(() => {
        this.request.delete("/article/" + row.id).then(resp => {
          //判断操作是否成功
          if (resp.flag) {
            this.$message.success(res.msg);
          } else {
            this.$message.error(res.msg)
          }
        }).finally(() => {//刷新页面
          this.load();
        });
      }).catch(() => {
        this.$message.info("当前操作取消");
      });

    },
    handleSelectionChange(val) { //多选控制

      this.multipleSelection = val;
    },
    delBatch() {//批量删除
      this.$confirm("此操作将永久删除当前信息，是否继续？", "提示", {type: "info"}).then(() => {
        //判断操作是否成功
        let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
        this.request.post("/article/del/batch", ids).then(res => {
          if (res.flag) {
            this.$message.success("批量删除成功");
            this.load();
          } else {
            this.$message.error("批量删除失败");
          }
        }).finally(() => {//刷新页面
          this.load();
        });
      }).catch(() => {
        this.$message.info("当前操作取消");
      });
    },
    handleSizeChange(pageSize) {
      this.pagination.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(current) {
      this.pagination.currentPage = current;
      this.load();
    }

  }
}
</script>

<style scoped>
.headerBg {
  background: #eee !important;
}
</style>
