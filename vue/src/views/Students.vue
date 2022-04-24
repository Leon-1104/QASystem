<template>
  <div>


    <div style="margin: 10px 0">
      <el-input style="width: 200px" v-model="pagination.studentId" placeholder="请输入学号"
                suffix-icon="el-icon-search" @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" v-model="pagination.studentName" placeholder="请输入姓名"
                suffix-icon="el-icon-message" class="ml-5" @keyup.enter.native="load"></el-input>
      <el-input style="width: 200px" v-model="pagination.major" placeholder="请输入专业" suffix-icon="el-icon-position"
                class="ml-5" @keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reSet">重置</el-button>
    </div>

    <div style="margin: 10px 0" v-if="this.role==='0'">
      <el-button type="primary" @click="handleSave">新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      <el-upload action="http://localhost:9090/students/import" :show-file-list="false" accept="xlsx"
                 :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>
    <!--        新增表单-->
    <el-dialog title="新增学生" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="80px" size="small" :rules="rules">
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="form.studentName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <!--              <el-select v-model="form.grade" placeholder="请选择年级">-->
          <!--                <el-option label="2019" value="2019"></el-option>-->
          <!--                <el-option label="2020" value="2020"></el-option>-->
          <!--                <el-option label="2021" value="2021"></el-option>-->
          <!--              </el-select>-->
          <el-input v-model="form.grade" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="form.major" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="班级" prop="cls">
          <el-input v-model="form.cls" autocomplete="off"></el-input>
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <!--        编辑表单-->
    <el-dialog title="编辑信息" :visible.sync="dialogFormVisibleEdit" width="30%">
      <el-form :model="form" label-width="80px" size="small" :rules="rules">
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="form.studentName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <!--              <el-select v-model="form.grade" placeholder="请选择年级">-->
          <!--                <el-option label="2019" value="2019"></el-option>-->
          <!--                <el-option label="2020" value="2020"></el-option>-->
          <!--                <el-option label="2021" value="2021"></el-option>-->
          <!--              </el-select>-->
          <el-input v-model="form.grade" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="form.major" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="班级" prop="cls">
          <el-input v-model="form.cls" autocomplete="off"></el-input>
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit= false">取 消</el-button>
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
          width="55" v-if="this.role==='0'">
      </el-table-column>
      <el-table-column prop="studentId" label="学号">
      </el-table-column>
      <el-table-column prop="studentName" label="姓名">
      </el-table-column>
      <el-table-column prop="gender" label="性别">
      </el-table-column>
      <el-table-column prop="grade" label="年级">
      </el-table-column>
      <el-table-column prop="major" label="专业">
      </el-table-column>
      <el-table-column prop="cls" label="班级">
      </el-table-column>
      <el-table-column prop="institute" label="学院" width="200px">
      </el-table-column>
      <el-table-column prop="tel" label="电话">
      </el-table-column>
      <el-table-column prop="email" label="邮箱">
      </el-table-column>
      <el-table-column prop="cardId" label="身份证号">
      </el-table-column>
      <el-table-column label="操作" width="200" align="center" v-if="this.role==='0'">
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
  name: "Students",
  data() {
    return {
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
        studentId: '',
        studentName: '',
        major: '',

      },
      rules: {
        studentName: [
          {required: true, message: '请输入学生姓名', trigger: 'blur'}
        ],
        gender: [
          {required: true, message: '请输入性别', trigger: 'blur'}
        ],
        grade: [
          {required: true, message: '请输入年级', trigger: 'blur'}
        ],
        major: [
          {required: true, message: '请输入专业', trigger: 'blur'}
        ], cls: [
          {required: true, message: '请输入班级', trigger: 'blur'}
        ],
        institute: [
          {required: true, message: '请输入学院', trigger: 'blur'}
        ], tel: [
          {required: true, message: '请输入电话', trigger: 'blur'}
        ], email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'}
        ], cardId: [{required: true, message: '请输入身份证', trigger: 'blur'}
        ]

      },
      role: this.$cookies.get("role"),
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      //拼接字符串进行多条件查询
      let param = "?studentId=" + this.pagination.studentId;
      param += "&studentName=" + this.pagination.studentName;
      param += "&major=" + this.pagination.major;
      this.request.get("/students/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param).then(resp => {
        this.pagination.total = resp.data.total;
        this.pagination.currentPage = resp.data.current;
        this.pagination.pageSize = resp.data.size;
        this.tableData = resp.data.records;
      })
    },
    exp() {//导出数据
      window.open("http://localhost:9090/students/export");
    },
    headerBg() {
      return 'background:#eee !important ;text-align: center';
    },
    cellStyle() {
      return '  text-align: center;font-size: 13px;'
    },
    handleSave() {//弹出新增窗口，并清空表单
      this.dialogFormVisible = true;
      this.form = {};
    },
    save() {//新增函数
      this.request.post("/students", this.form).then(resp => {
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
    handleExcelImportSuccess() {
      this.$message.success("导入成功");
      this.load();
    },
    handleEdit(row) {//弹出编辑窗口
      this.dialogFormVisibleEdit = true;
      this.request.get("/students/" + row.studentId).then(resp => {
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
      this.request.put("/students", this.form).then(resp => {
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
      this.pagination.studentName = "";
      this.pagination.studentId = "";
      this.pagination.major = "";
      this.load();

    },
    handleDelete(row) {//删除
      this.$confirm("此操作将永久删除当前信息，是否继续？", "提示", {type: "info"}).then(() => {
        this.request.delete("/students/" + row.studentId).then(resp => {
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
        let ids = this.multipleSelection.map(v => v.studentId)  // [{}, {}, {}] => [1,2,3]
        this.request.post("/students/del/batch", ids).then(res => {
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
    },
  }
}
</script>

