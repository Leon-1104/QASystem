<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" v-model="pagination.courseName" placeholder="请输入课程名"
                suffix-icon="el-icon-message" class="ml-5" @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" v-model="pagination.studentName" placeholder="请输入学生姓名"
                suffix-icon="el-icon-position" class="ml-5" @keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reSet">重置</el-button>
    </div>

    <!--        编辑表单-->
    <el-dialog title="拒绝当前预约" :visible.sync="dialogFormVisibleRefuse" width="30%">
      <el-form :model="formData" label-width="70px" :rules="rules" size="small">
        <el-form-item label="教师姓名">
          <el-input v-model="formData.teacherName" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程">
          <el-select v-model="formData.courseName" disabled>
          </el-select>
        </el-form-item>
        <el-form-item label="办公室">
          <el-input v-model="formData.office" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="formData.studentName" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="预约时间">
          <el-input v-model="formData.resTime" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" placehodler="请输入拒绝理由" maxline="30"
                    v-model="formData.remark"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleRefuse = false">取 消</el-button>
        <el-button type="primary" @click="saveRefuse">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="tableData"
        border stripe
        :header-cell-style="headerBg"
        :cell-style="cellStyle"
        @selection-change="handleSelectionChange"
        tooltip-effect="dark"
    >
      <!--      <el-table-column-->
      <!--          type="selection"-->
      <!--          width="55">-->
      <!--      </el-table-column>-->
      <el-table-column prop="id" label="预约信息编号">
      </el-table-column>
      <el-table-column prop="courseName" label="课程名">
      </el-table-column>
      <el-table-column prop="teacherName" label="教师姓名">
      </el-table-column>
      <el-table-column prop="studentName" label="学生姓名">
      </el-table-column>
      <el-table-column prop="resTime" width="200" label="答疑时间">
      </el-table-column>
      <el-table-column prop="office" label="办公室">
      </el-table-column>
      <el-table-column prop="createTime" width="200" label="申请时间">
      </el-table-column>
      <el-table-column prop="acceptFlag" label="当前状态">
        <template v-slot="scope">
          <i class="el-icon-info" v-if="scope.row.acceptFlag==='WAIT'" style="color: dodgerblue">待处理</i>
          <i class="el-icon-success" v-else-if="scope.row.acceptFlag==='ACCEPT'" style="color: #42b983">已接受</i>
          <i class="el-icon-error" v-else-if="scope.row.acceptFlag==='REFUSE'" style="color: red">已拒绝</i>
<!--          <i class="el-icon-info" v-else-if="Date.parse(scope.row.resTime)<Date.now()" style="color: gray">已过期</i>-->
        </template>
      </el-table-column>
      <el-table-column label="是否过期">
        <template v-slot="scope">
          <i class="el-icon-info" v-if="isOutDate(scope.row.resTime)" style="color: gray">已过期</i>
          <i class="el-icon-success" v-else style="color: #42b983">未过期</i>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope" v-if="Date.parse(scope.row.resTime)>Date.now()&&scope.row.acceptFlag=='WAIT'">
          <el-button type="success" @click="handleAccept(scope.row)">接受 <i class="el-icon-circle-check"></i></el-button>
          <el-button type="danger" @click="handleRefuse(scope.row)">拒绝 <i class="el-icon-circle-close"></i>
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
      dialogFormVisibleRefuse: false,
      dialogFormVisibleEdit: false,
      formData: {},//表单数据
      pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 10,//每页显示的记录数
        total: 0,//总记录数
        courseName: '',
        studentName: '',

      },
      rules: {
        remark: [
          {required: true, message: '拒绝理由不能为空', trigger: 'blur'},
        ]
      },
      teacherId: this.$cookies.get("cid"),
      user: localStorage.getItem("user") ? JSON.stringify("user") : {},
      tempTableDate: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      //拼接字符串进行多条件查询
      let param = "?courseName=" + this.pagination.courseName;
      param += "&studentName=" + this.pagination.studentName;
      this.request.get("/resInfos/t/" + this.pagination.currentPage +
          "/" + this.pagination.pageSize + '/' + this.teacherId + param).then(resp => {
        this.pagination.total = resp.data.total;
        this.pagination.currentPage = resp.data.current;
        this.pagination.pageSize = resp.data.size;
        resp.data.records.forEach(item => {
          item.resTime = this.formatDateTime(item.resTime)
        })
        this.tableData = resp.data.records;
      })
    },
    isOutDate(resTime) {

      return Date.parse(resTime) < Date.now()
    },
    handleAccept(row) {
      if (this.isOutDate(row.resTime)) {
        this.$message("当前预约已经过期，您无法操作。");
        return null;
      } else {
        this.request.get("/resInfos/" + row.id).then(resp => {
          if (resp.flag && resp.data != null) {
            this.formData = resp.data;
            this.formData.acceptFlag = 'ACCEPT'
            this.request.put("/resInfos", this.formData).then(resp => {
              if (resp.flag) {
                this.$message.success('您已接受当前预约')
              } else {
                this.$message.error('接受失败，系统错误')
              }
            })

          } else {
            this.$message.error("获取当前预约信息失败")
          }
        })
      }

    },
    handleRefuse(row) {
      if (this.isOutDate(row.resTime)) {
        this.$message("当前预约已经过期，您无法操作。");
        return null;
      } else {
        this.dialogFormVisibleRefuse = true;
        this.request.get("/resInfos/" + row.id).then(resp => {
          if (resp.flag && resp.data != null) {
            this.formData = resp.data;
          } else {
            this.$message.error(resp.msg)
          }
        })
      }
    },

    saveRefuse() {

      this.formData.acceptFlag = 'REFUSE'
      this.request.put("/resInfos", this.formData).then(resp => {
        if (resp.flag) {
          this.$message.success('您已拒绝当前预约')
          this.dialogFormVisibleRefuse = false;
          location.reload();
        } else {
          this.$message.error('拒绝失败，系统错误')
        }
      })
    },
    headerBg() {
      return 'background:#eee !important ;text-align: center';

    },
    cellStyle() {
      return 'text-align: center;font-size: 13px;'
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
    reSet() {//重置搜索框
      this.pagination.courseName = "";
      this.pagination.teacherName = '';
      this.load();
    },
    handleSelectionChange(val) { //多选控制

      this.multipleSelection = val;
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
