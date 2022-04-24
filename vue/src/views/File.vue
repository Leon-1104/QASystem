<template>
  <div>


    <div style="margin: 10px 0">
      <el-input style="width: 200px" v-model="pagination.name" placeholder="请输入文件名" suffix-icon="el-icon-position"
                class="ml-5" @keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reSet">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      <el-upload action="http://localhost:9090/file/upload" :show-file-list="false"
                 :on-success="handleFileUploadSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">上传 <i class="el-icon-upload2"></i></el-button>
      </el-upload>
    </div>

    <el-table
        :data="tableData"
        border stripe
        :header-cell-class-name="headerBg"
        @selection-change="handleSelectionChange"
        tooltip-effect="dark"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="文件名称"></el-table-column>
      <el-table-column prop="type" label="文件类型"></el-table-column>
      <el-table-column prop="size" label="文件大小(kb)"></el-table-column>
      <el-table-column label="下载">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">下载<i class="el-icon-download"style="margin-left: 5px" ></i></el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc"
                     @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="danger" @click="handleDelete(scope.row)">删除 <i class="el-icon-remove-outline"></i>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagination.currentPage"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pagination.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "Students",
  data() {
    return {
      tableData: [],
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,//侧边栏是否收缩
      sideWidth: 200,
      logoTextShow: true,//侧边栏名称是否显示
      headerBg: 'headerBg',//表格头部北京
      dialogFormVisible: false,
      dialogFormVisibleEdit: false,
      form: {},//表单数据
      pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 10,//每页显示的记录数
        total: 0,//总记录数
        id: '',
        name: '',
        major: ''
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      //拼接字符串进行多条件查询
      let param = "?name=" + this.pagination.name;

      this.request.get("/file/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param).then(resp => {
        this.pagination.total = resp.data.total;
        this.pagination.currentPage = resp.data.current;
        this.pagination.pageSize = resp.data.size;
        this.tableData = resp.data.records;

      })
    },
    download(url) {
      window.open(url);
    },
    handleFileUploadSuccess() {
      this.$message.success("导入成功");
      this.load();
    },
    reSet() {//重置搜索框
      this.pagination.name = "";
      this.load();
    },
    handleDelete(row) {//删除
      this.$confirm("此操作将永久删除当前信息，是否继续？", "提示", {type: "info"}).then(() => {
        this.request.delete("/file/" + row.id).then(resp => {
          //判断操作是否成功
          if (resp.flag) {
            this.$message.success(resp.msg);
          } else {
            this.$message.error(resp.msg)
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
        this.request.post("/file/del/batch", ids).then(res => {
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
    changeEnable(row) {
      this.request.put("/file", row).then(res => {
        if (res.flag) {
          this.$message.success("操作成功")
        }
      })
    },
    handleSizeChange(pageSize) {
      this.pagination.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(current) {
      this.pagination.currentPage = current;
      this.load();
    },
  }
}
</script>

<style scoped>
.headerBg {
  background: #eee !important;
}
</style>
