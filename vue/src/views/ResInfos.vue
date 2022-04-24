<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" v-model="pagination.courseName" placeholder="请输入课程名"
                suffix-icon="el-icon-message" class="ml-5" @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" v-model="pagination.teacherName" placeholder="请输入教师姓名"
                suffix-icon="el-icon-position" class="ml-5" @keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reSet">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>
    <el-table
        :data="tableData"
        border stripe
        :header-cell-style="headerBg"
        :cell-style="cellStyle"
        @selection-change="handleSelectionChange"
        tooltip-effect="dark"
    >
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="预约信息编号">
      </el-table-column>
      <el-table-column prop="courseName" label="课程名">
      </el-table-column>
      <el-table-column prop="teacherName" label="教师姓名">
      </el-table-column>
      <el-table-column prop="studentName" label="学生姓名">
      </el-table-column>
      <el-table-column prop="resTime" label="答疑时间" width="200">
      </el-table-column>
      <el-table-column prop="office" label="办公室">
      </el-table-column>
      <el-table-column prop="createTime" label="申请时间" width="200">
      </el-table-column>
      <el-table-column prop="acceptFlag" label="当前状态">
        <template v-slot="scope">
          <i class="el-icon-info" v-if="scope.row.acceptFlag==='WAIT'" style="color: dodgerblue">待处理</i>
          <i class="el-icon-success" v-else-if="scope.row.acceptFlag==='ACCEPT'" style="color: #42b983">已接受</i>
          <i class="el-icon-error" v-else-if="scope.row.acceptFlag==='REFUSE'" style="color: red">已拒绝</i>
          <i class="el-icon-info" v-else-if="Date.parse(scope.row.resTime)<Date.now()" style="color: gray">已过期</i>
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
  name: "ResInfos",
  data() {
    return {
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
        courseName: '',
        teacherName: '',
        studentName: '',
      },
      user: localStorage.getItem("user") ? JSON.stringify("user") : {},
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      //拼接字符串进行多条件查询

      let param = "?courseName=" + this.pagination.courseName;
      param += "&teacherName=" + this.pagination.teacherName;
      param += "&studentName=" + this.pagination.studentName;
      this.request.get("/resInfos/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param).then(resp => {
        this.pagination.total = resp.data.total;
        this.pagination.currentPage = resp.data.current;
        this.pagination.pageSize = resp.data.size;
        resp.data.records.forEach(item => {
          item.resTime = this.formatDateTime(item.resTime)

        })
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
    }, headerBg() {
      return 'background:#eee !important ;text-align: center';
    },
    cellStyle() {
      return '  text-align: center;font-size: 13px;'
    },
    formatDateTime(inputTime) {//处理后端发来的时间数据
      if (!inputTime && typeof inputTime !== 'number') {
        return '';
      }
      var localTime = '';
      inputTime = new Date(inputTime).getTime();
      const offset = (new Date()).getTimezoneOffset();
      localTime = (new Date(inputTime + offset * 60000)).toISOString();
      localTime = localTime.substr(0, localTime.lastIndexOf('.'));
      localTime = localTime.replace('T', ' ');
      return localTime;
    },
    exp() {
      window.open("http://localhost:9090/resInfos/export");
    },
    reSet() {//重置搜索框
      this.pagination.courseName = "";
      this.pagination.teacherName = '';
      this.load();
    },
    handleDelete(row) {//删除
      this.$confirm("此操作将永久删除当前信息，是否继续？", "提示", {type: "info"}).then(() => {
        this.request.delete("/resInfos/" + row.courseId).then(resp => {
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
        let ids = this.multipleSelection.map(v => v.courseId)  // [{}, {}, {}] => [1,2,3]
        this.request.post("/resInfos/del/batch", ids).then(res => {
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
