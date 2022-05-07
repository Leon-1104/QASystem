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
      <el-button type="primary" @click="handleSave">新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      <el-upload action="http://localhost:9090/courses/import" :show-file-list="false" accept="xlsx"
                 :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>
    <!--            新增表单-->
    <el-dialog title="新增课程" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="80px" size="small" :rules="rules">
        <el-form-item label="课程名" prop="courseName">
          <el-input v-model="form.courseName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="教师姓名" prop="teacherName">
          <el-select v-model="form.teacherName" placeholder="请选择">
            <el-option
                v-for="(item,index) in teachers"
                :key="index"
                :label="item.teacherName"
                :value="item.teacherName">
            </el-option>
          </el-select>
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
        <el-form-item label="课程名" prop="courseName">
          <el-input v-model="form.courseName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="教师姓名" prop="teacherName">
          <el-select v-model="form.teacherName" placeholder="请选择">
            <el-option
                v-for="(item,index) in teachers"
                :key="index"
                :label="item.teacherName"
                :value="item.teacherName">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit()">确 定</el-button>
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
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="courseId" label="课程编号">
      </el-table-column>
      <el-table-column prop="courseName" label="课程名">
      </el-table-column>
      <el-table-column prop="teacherName" label="教师姓名">

      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
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
  name: "Courses",
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
        teacherId: '',
        type: ''
      },
      rules: {
        courseName: {
          required: true, message: '请输入课程姓名', trigger: 'blur'
        },
        teacherName: {
          required: true, message: '请选择教师', trigger: 'blur'
        },
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

      let param = "?courseName=" + this.pagination.courseName;
      param += "&teacherName=" + this.pagination.teacherName;
      this.request.get("/courses/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param).then(resp => {
        this.pagination.total = resp.data.total;
        this.pagination.currentPage = resp.data.current;
        this.pagination.pageSize = resp.data.size;
        this.tableData = resp.data.records;


        // // 去重
        // let tempData2 = resp.data.records;
        // let hash = {};
        // tempData2 = tempData2.reduce(function (item, next) {
        //   hash[next.teacherName] ? '' : hash[next.teacherName] = true && item.push(next);
        //   return item;
        // }, []);
        // this.teachers = tempData2;


      })
      let param2 = "?teacherId=" + this.pagination.teacherId;
      param2 += "&teacherName=" + this.pagination.teacherName;
      param2 += "&type=" + this.pagination.type;
      this.request.get("/teachers/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param2).then(resp => {
        // this.pagination.total = resp.data.total;
        // this.pagination.currentPage = resp.data.current;
        // this.pagination.pageSize = resp.data.size;
        // 去重
        let tempData2 = resp.data.records;
        let hash = {};
        tempData2 = tempData2.reduce(function (item, next) {
          hash[next.teacherName] ? '' : hash[next.teacherName] = true && item.push(next);
          return item;
        }, []);
        this.teachers = tempData2;
      })
    },
    exp() {
      window.open("http://localhost:9090/courses/export");
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功");
      this.load();
    },
    handleSave() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    save() {//新增函数
      this.request.post("/courses", this.form).then(resp => {

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
      this.request.get("/courses/" + row.courseId).then(resp => {
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
      this.request.put("/courses", this.form).then(resp => {

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
      this.pagination.courseName = "";
      this.pagination.teacherName = '';
      this.load();
    },
    handleDelete(row) {//删除
      this.$confirm("此操作将永久删除当前信息，是否继续？", "提示", {type: "info"}).then(() => {
        this.request.delete("/courses/" + row.courseId).then(resp => {
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
        let ids = this.multipleSelection.map(v => v.courseId)  // [{}, {}, {}] => [1,2,3]
        this.request.post("/courses/del/batch", ids).then(res => {
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
    }, headerBg() {
      return 'background:#eee !important ;text-align: center';
    },
    cellStyle() {
      return '  text-align: center;font-size: 13px;'
    },

  }
}
</script>


