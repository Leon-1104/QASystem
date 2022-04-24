<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" v-model="pagination.name" placeholder="请输入文章名"
                suffix-icon="el-icon-message" class="ml-5" @keyup.enter.native="load"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reSet">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleSave">新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
    </div>
    <!--            新增表单-->
    <el-dialog title="新增文章" :visible.sync="dialogFormVisible" width="60%">
      <el-form :model="form" label-width="70px" size="small">
        <el-form-item label="文章标题">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="文章类型">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option
                v-for="item in types"
                :label="item.label"
                :value="item.value"
                :key="item"
            ></el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <mavon-editor ref="md" v-model="form.content" :ishljs="true" @imgAdd="imgAdd"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <!--        编辑表单-->
    <el-dialog title="编辑文章" :visible.sync="dialogFormVisibleEdit" width="60%">
      <el-form :model="form" label-width="70px" size="small">
        <el-form-item label="文章标题">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <mavon-editor ref="md" v-model="form.content" :ishljs="true" @imgAdd="imgAdd"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit()">确 定</el-button>
      </div>
    </el-dialog>


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
      <el-table-column prop="id" label="文章编号">
      </el-table-column>
      <el-table-column prop="name" label="文章标题">
      </el-table-column>
      <el-table-column prop="content" label="文章内容">
        <template v-slot="scope">
          <el-button @click="viewArticle(scope.row.content) " type="primary">
            查看内容
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="time" label="发布时间">
      </el-table-column>
      <el-table-column prop="user" label="发布人">
      </el-table-column>
      <el-table-column prop="type" label="类型">
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
      types: [
        {label: '新闻', value: 'NEWS'},
        {label: '公告', value: 'ANN'},
      ],
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
