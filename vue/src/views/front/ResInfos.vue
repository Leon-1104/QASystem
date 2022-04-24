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
    <!--        &lt;!&ndash;        编辑表单&ndash;&gt;-->
    <!--        <el-dialog title="编辑信息" :visible.sync="dialogFormVisibleEdit" width="30%">-->
    <!--          <el-form :model="form" label-width="70px" size="small">-->
    <!--            <el-form-item label="课程名">-->
    <!--              <el-input v-model="form.courseName" autocomplete="off"></el-input>-->
    <!--            </el-form-item>-->
    <!--            <el-form-item label="教师姓名">-->
    <!--              <el-select v-model="form.teacherName" placeholder="请选择">-->
    <!--                <el-option-->
    <!--                    v-for="item in teachers"-->
    <!--                    :label="item.teacherName"-->
    <!--                    :value="item.teacherName">-->
    <!--                </el-option>-->
    <!--              </el-select>-->
    <!--            </el-form-item>-->
    <!--          </el-form>-->
    <!--          <div slot="footer" class="dialog-footer">-->
    <!--            <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>-->
    <!--            <el-button type="primary" @click="saveEdit()">确 定</el-button>-->
    <!--          </div>-->
    <!--        </el-dialog>-->
    <el-table
        :data="tableData"
        border stripe
        :header-cell-style="headerBg"
        :cell-style="cellStyle"
        tooltip-effect="dark"

    >

      <el-table-column prop="id" label="预约信息编号">
      </el-table-column>
      <el-table-column prop="courseName" label="课程名">
      </el-table-column>
      <el-table-column prop="teacherName" label="教师姓名">
      </el-table-column>
      <el-table-column prop="studentName" label="学生姓名">
      </el-table-column>
      <el-table-column prop="resTime" label="答疑时间" width="180px">
      </el-table-column>
      <el-table-column prop="office" label="办公室">
      </el-table-column>
      <el-table-column prop="createTime" label="申请时间" width="180px">
      </el-table-column>
      <el-table-column property="acceptFlag" label="当前状态">
        <template v-slot="scope">
          <i class="el-icon-info" v-if="scope.row.acceptFlag==='WAIT'" style="color: dodgerblue">待处理</i>
          <i class="el-icon-success" v-else-if="scope.row.acceptFlag==='ACCEPT'" style="color: #42b983">已接受</i>
          <i class="el-icon-error" v-else-if="scope.row.acceptFlag==='REFUSE'" style="color: red">已拒绝</i>

        </template>
      </el-table-column>
      <el-table-column label="是否过期">
        <template v-slot="scope">
          <i class="el-icon-info" v-if="isOutDate(scope.row.resTime)" style="color: gray">已过期</i>
          <i class="el-icon-success" v-else style="color: #42b983">未过期</i>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="300px">
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
      Flags: [],
      teachers: [],
      tableData: [],
      tempTableData: [],
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,//侧边栏是否收缩
      sideWidth: 200,
      studentId:this.$cookies.get("cid"),
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
      },

    }
  },
  created() {
    this.load()
  },
  methods: {
    isOutDate(resTime) {

      return Date.parse(resTime) < Date.now()
    },
    load() {
      //拼接字符串进行多条件查询
      let param = "?courseName=" + this.pagination.courseName;
      param += "&teacherName=" + this.pagination.teacherName;
      this.request.get("/resInfos/s/" + this.pagination.currentPage +
          "/" + this.pagination.pageSize +'/'+this.studentId+ param).then(resp => {
        this.pagination.total = resp.data.total;
        this.pagination.currentPage = resp.data.current;
        this.pagination.pageSize = resp.data.size;
        resp.data.records.forEach(item=>{
          item.resTime=this.formatDateTime(item.resTime)
        })
        this.tableData = resp.data.records;
      })
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
    handleSizeChange(pageSize) {
      this.pagination.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(current) {
      this.pagination.currentPage = current;
      this.load();
    },
    headerBg() {
      return 'background:#eee !important ;text-align: center';
    },
    cellStyle() {
      return '  text-align: center;font-size: 13px;'
    }

  }
}
</script>

<style scoped>


</style>
