<template>
  <div>


    <div style="margin: 10px 0">
      <el-input style="width: 200px" v-model="pagination.teacherName" placeholder="请输入教师姓名"
                suffix-icon="el-icon-message" class="ml-5" @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" v-model="pagination.type" placeholder="请输入职称" suffix-icon="el-icon-position"
                class="ml-5" @keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reSet">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleSave">新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      <el-upload action="http://localhost:9090/teachers/import" :show-file-list="false" accept="xlsx"
                 :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>
    <!--        新增表单-->
    <el-dialog title="新增教师" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="80px" size="small" :rules="rules">
        <el-form-item label="姓名" prop="teacherName">
          <el-input v-model="form.teacherName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学院" prop="institute">
          <el-input v-model="form.institute" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="form.tel" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="cardId">
          <el-input v-model="form.cardId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="职称" prop="type">
          <el-input v-model="form.type" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="办公室" prop="office">
          <el-input v-model="form.office" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <!--        编辑表单-->
    <el-dialog title="编辑信息" :visible.sync="dialogFormVisibleEdit" width="30%">
      <el-form :model="form" label-width="80px" size="small" :rules="rules">

        <el-form-item label="姓名" prop="teacherName">
          <el-input v-model="form.teacherName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学院" prop="institute">
          <el-input v-model="form.institute" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="form.tel" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="cardId">
          <el-input v-model="form.cardId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="职称" prop="type">
          <el-input v-model="form.type" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="办公室" prop="office">
          <el-input v-model="form.office" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit()">确 定</el-button>
      </div>
    </el-dialog>
    <!--        编辑表单-->
    <el-dialog title="课程信息" :visible.sync="dialogFormVisibleCourses" width="30%">
      <el-table
          :data="courses"
          border stripe
          :header-cell-class-name="headerBg"
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

    <el-table
        :data="tableData"
        border stripe
        :header-cell-style="headerBg"
        :cell-style="cellStyle"
        @selection-change="handleSelectionChange"
        tooltip-effect="dark">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="teacherId" label="教师编号">
      </el-table-column>
      <el-table-column prop="teacherName" label="姓名">
      </el-table-column>
      <el-table-column prop="gender" label="性别">
      </el-table-column>
      <el-table-column prop="institute" label="学院" width="200px">
      </el-table-column>
      <el-table-column prop="tel" label="电话">
      </el-table-column>
      <el-table-column prop="email" label="邮箱">
      </el-table-column>
      <el-table-column prop="cardId" label="身份证号">
      </el-table-column>
      <el-table-column prop="type" label="职称">
      </el-table-column>
      <el-table-column prop="office" label="办公室">
      </el-table-column>
      <el-table-column label="操作" width="350" align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="handleLookCourses(scope.row)">查看教授课程 <i class="el-icon-reading"></i>
          </el-button>
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
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
  name: "Teachers",
  data() {
    return {
      courses: [],
      tableData: [],
      msg: "hello leon",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,//侧边栏是否收缩
      sideWidth: 200,
      logoTextShow: true,//侧边栏名称是否显示

      dialogFormVisible: false,
      dialogFormVisibleEdit: false,
      dialogFormVisibleCourses: false,
      form: {},//表单数据
      pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 10,//每页显示的记录数
        total: 0,//总记录数
        teacherId: '',
        teacherName: '',
        type: ''
      }, rules: {
        teacherName: [
          {required: true, message: '请输入教师姓名', trigger: 'blur'}
        ],
        gender: [
          {required: true, message: '请输入性别', trigger: 'blur'}
        ],
        office: [
          {required: true, message: '请输入办公室', trigger: 'blur'}
        ],
        institute: [
          {required: true, message: '请输入学院', trigger: 'blur'}
        ], tel: [
          {required: true, message: '请输入电话', trigger: 'blur'}
        ], email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'}
        ],
        cardId: [{required: true, message: '请输入身份证', trigger: 'blur'}
        ],
        type: [{required: true, message: '请输入职称', trigger: 'blur'}
        ],


      },
    user: localStorage.getItem("user") ? JSON.stringify("user") : {}
  }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      //拼接字符串进行多条件查询
      let param = "?teacherId=" + this.pagination.teacherId;
      param += "&teacherName=" + this.pagination.teacherName;
      param += "&type=" + this.pagination.type;
      this.request.get("/teachers/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param).then(resp => {
        this.pagination.total = resp.data.total;
        this.pagination.currentPage = resp.data.current;
        this.pagination.pageSize = resp.data.size;
        this.tableData = resp.data.records;
        // console.log(this.tableData)
      })
    },
    exp() {
      window.open("http://localhost:9090/teachers/export");
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功");
      this.load();
    },
    save() {//新增函数
      this.request.post("/teachers", this.form).then(resp => {
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
    handleSave() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    handleLookCourses(row) {//编辑查看课程信息
      this.dialogFormVisibleCourses = true;
      this.courses = row.courses;
    },
    handleEdit(row) {//弹出编辑窗口
      this.dialogFormVisibleEdit = true;
      this.request.get("/teachers/" + row.teacherId).then(resp => {
        if (resp.flag && resp.data != null) {
          this.form = resp.data;
        } else {
          this.$message.error(resp.msg)
        }
      }).finally(() => {
        this.load();
      });
    },
    saveEdit() { //保存编辑
      this.request.put("/teachers", this.form).then(resp => {

        if (resp.flag) {
          this.dialogFormVisibleEdit = false;
          this.$message.success(resp.msg);
        } else {
          this.$message.error(resp.msg);
        }
      }).finally(() => {
        this.load();
      });
    },
    reSet() {//重置搜索框
      this.pagination.teacherName = "";
      this.pagination.teacherId = "";
      this.pagination.type = "";
      this.load();
    },
    handleDelete(row) {//删除
      this.$confirm("此操作将永久删除当前信息，是否继续？", "提示", {type: "info"}).then(() => {
        this.request.delete("/teachers/" + row.teacherId).then(resp => {
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
        let ids = this.multipleSelection.map(v => v.teacherId)  // [{}, {}, {}] => [1,2,3]
        this.request.post("/teachers/del/batch", ids).then(res => {
          if (res.flag) {
            this.$message.success(res.msg);
            this.load();
          } else {
            this.$message.error(res.msg);
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
    }, headerBg() {
      return 'background:#eee !important ;text-align: center';
    }
    ,
    cellStyle() {
      return '  text-align: center;font-size: 13px;'
    },
  }
}
</script>


