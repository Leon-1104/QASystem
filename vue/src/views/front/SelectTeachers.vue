<template>
  <div>


    <div style="margin: 10px 0">
      <el-input style="width: 200px" v-model="pagination.teacherName" placeholder="请输入姓名"
                suffix-icon="el-icon-message" class="ml-5" @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" v-model="pagination.type" placeholder="请输入职称" suffix-icon="el-icon-position"
                class="ml-5" @keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reSet">重置</el-button>
    </div>

    <!--        编辑表单-->
    <el-dialog title="课程信息" :visible.sync="dialogFormVisibleCourses" width="30%">
      <el-table
          :data="courses"
          border stripe
          :header-cell-style="headerBg"
          :cell-style="cellStyle"
          tooltip-effect="dark"
      >
        <el-table-column prop="courseId" label="课程编号">
        </el-table-column>
        <el-table-column prop="courseName" label="课程名">
        </el-table-column>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisibleCourses = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisibleCourses = false">确 定</el-button>
        </div>
      </el-table>
    </el-dialog>
    <!--        预约表单-->
    <el-dialog title="预约答疑信息" :visible.sync="dialogFormVisibleRes" width="30%">
      <el-form :model="form" label-width="70px" size="small">
        <el-form-item label="教师姓名">
          <el-input v-model="form.teacherName" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程">
          <el-select v-model="form.courseName" placeholder="请选择课程">
            <el-option
                v-for="(item,index) in courses"
                :key="index"
                :label="item.courseName"
                :value="item.courseName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="办公室">
          <el-input v-model="form.office" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="form.studentName" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="预约时间">
          <el-date-picker
              v-model="form.resTime"
              type="datetime"
              placeholder="选择日期时间"
              align="right"
              :picker-options="pickerOptions"
              @change="ChangeHandler($event,form.resTime)"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleRes = false">取 消</el-button>
        <el-button type="primary" @click="saveRes()">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="tableData"
        border stripe
        :header-cell-style="headerBg"
        :cell-style="cellStyle"
        tooltip-effect="dark"
    >
      <el-table-column prop="teacherName" label="姓名">
      </el-table-column>
      <el-table-column prop="institute" label="学院" width="200px">
      </el-table-column>
      <el-table-column prop="type" label="职称">
      </el-table-column>
      <el-table-column prop="office" label="办公室">
      </el-table-column>
      <el-table-column prop="email" label="邮箱">
      </el-table-column>
      <el-table-column label="操作" width="350" align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="handleLookCourses(scope.row)">查看教授课程 <i class="el-icon-reading"></i>
          </el-button>
          <el-button type="success" @click="handleRes(scope.row)">预约 <i class="el-icon-edit"></i></el-button>
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
  name: "SelectTeachers",
  data() {
    return {
      courses: [],
      tableData: [],
      dialogFormVisibleCourses: false,
      dialogFormVisibleRes: false,
      form: {},//表单数据
      pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 10,//每页显示的记录数
        total: 0,//总记录数
        teacherName: '',
        type: ''
      },
      pickerOptions: {//设置禁用日期和时间段
        disabledDate: time => {//禁用休息日和下班时间
          return time.getDay() === 6 || time.getDay() === 0 || time.getTime() < Date.now();
        },
        selectableRange: ['8:30:00 - 11:30:00', '14:00:00 - 17:00:00']//设置为上班时间
      },
      user: localStorage.getItem("user") ? JSON.stringify("user") : {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    headerBg() {
      return 'background:#eee !important ;text-align: center';

    },
    cellStyle() {
      return '  text-align: center;font-size: 13px;'
    },
    load() {
      //拼接字符串进行多条件查询
      //
      let param = "?teacherName=" + this.pagination.teacherName;
      param += "&type=" + this.pagination.type;
      this.request.get("/teachers/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param).then(resp => {
        this.pagination.total = resp.data.total;
        this.pagination.currentPage = resp.data.current;
        this.pagination.pageSize = resp.data.size;
        this.tableData = resp.data.records;
      })
    },

    ChangeHandler(value, time) {//控制时间选择函数
      if (value != null) {
        if (value.getDay() === 6 || value.getDay() === 0) {
          this.form.resTime = '';
          this.$message.error("您选择的时间有误，请重新选择");
        }
      }

    },
    handleRes(row) {
      this.form = {};
      this.dialogFormVisibleRes = true;
      this.courses = row.courses;
      this.form.studentName = this.$cookies.get('cname');
      this.request.get("/teachers/" + row.teacherId).then(resp => {
        if (resp.flag && resp.data != null) {
          this.form.teacherName = resp.data.teacherName;
          this.form.office = resp.data.office;

        } else {
          this.$message.error(res.msg)
        }
      }).finally(() => {
        this.load();
      });


    },
    saveRes() {
      this.form.acceptFlag = 'WAIT'



      this.request.post("/resInfos", this.form).then(resp => {

        if (resp.flag) {
          this.dialogFormVisibleRes = false;
          this.$message.success(resp.msg);
        } else {
          this.$message.error(resp.msg);
        }
      }).finally(() => {
        this.load();
      });
    },
    handleLookCourses(row) {//编辑查看课程信息
      this.dialogFormVisibleCourses = true;
      this.courses = row.courses;
    },
    reSet() {//重置搜索框
      this.pagination.teacherName = "";
      this.pagination.teacherId = "";
      this.pagination.type = "";
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
  }
}
</script>

<style scoped>
</style>
