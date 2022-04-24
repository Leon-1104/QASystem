<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" v-model="pagination.name" placeholder="请输入角色名"
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
    <el-dialog title="新增角色" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="80px" size="small" :rules="rules">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识" prop="role">
          <el-select v-model="form.role" placeholder="请选择">
            <el-option
                v-for="(item,index) in roleTypes"
                :key="index"
                :label="item.label"
                :value="item.value">
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
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识" prop="role">
          <el-select v-model="form.role" placeholder="请选择">
            <el-option
                v-for="(item,index) in roleTypes"
                :key="index"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit()">确 定</el-button>
      </div>
    </el-dialog>
    <!--        分配菜单-->
    <el-dialog title="菜单分配" :visible.sync="menuDialogVis" width="30%">
      <el-tree
          :data="menuData"
          show-checkbox
          node-key="id"
          :default-checked-keys="checks"
          :default-expanded-keys="expends"
          ref="tree"
          :props="defaultProps">
      <span class="custom-tree-node" slot-scope="{ node, data }">
            <span><i :class="data.icon"></i> {{ data.name }}</span>
         </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVis = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="tableData"
        border stripe
        :header-cell-class-name="headerBg"
        @selection-change="handleSelectionChange"
        tooltip-effect="dark"
    >
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="编号">
      </el-table-column>
      <el-table-column prop="name" label="角色名称">
      </el-table-column>
      <el-table-column prop="description" label="描述">
      </el-table-column>
      <el-table-column prop="role" label="唯一标识">
      </el-table-column>
      <el-table-column label="操作" width="280" align="center">
        <template slot-scope="scope">
          <el-button type="info" @click="selectMenu(scope.row)">分配菜单 <i class="el-icon-menu"></i></el-button>
          <el-button type="success" @click="handleEdit(scope.row)" v-if="scope.row.role!=='0'">编辑 <i
              class="el-icon-edit"></i></el-button>
          <el-button type="danger" @click="handleDelete(scope.row)" v-if="scope.row.role!=='0'">删除 <i
              class="el-icon-remove-outline"></i>
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
  name: "Role",
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
      menuDialogVis: false,
      form: {},//表单数据
      menuData: [],
      defaultProps: {
        label: 'name'
      },
      rules: {
        name: [{required: true, message: '请输入名称', trigger: 'blur'}
        ],
        role: [
          {required: true, message: '请输入唯一标识', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入描述', trigger: 'blur'}
        ]
      },
      pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 10,//每页显示的记录数
        total: 0,//总记录数
        name: ''
      },
      user: localStorage.getItem("user") ? JSON.stringify("user") : {},
      expends: [],
      checks: [],
      roleId: 0,
      roleFlag: '',
      roleTypes:
          [{
            id: 1,
            label: "管理员",
            value: '0'
          },
            {
              id: 2,
              label: "老师",
              value: '1'
            },
            {
              id: 3,
              label: "学生",
              value: '2'
            }]
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      //拼接字符串进行多条件查询

      let param = "?name=" + this.pagination.name;
      this.request.get("/role/" + this.pagination.currentPage + "/" + this.pagination.pageSize + param).then(resp => {
        this.pagination.total = resp.data.total;
        this.pagination.currentPage = resp.data.current;
        this.pagination.pageSize = resp.data.size;
        this.tableData = resp.data.records;


      })
    },
    handleSave() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    save() {//新增函数
      this.request.post("/role", this.form).then(resp => {

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
      this.request.get("/role/" + row.id).then(resp => {
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
      this.request.put("/role", this.form).then(resp => {

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
    saveRoleMenu() {

      this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(resp => {
        if (resp.flag) {
          this.$message.success("保存成功！");
          this.menuDialogVis = false;
          //操作管理员后才重新登录
          if (this.roleFlag === '0') {
            this.$store.commit('logout');
          }

        } else {
          this.$message.error("保存失败");
        }
      })
    },
    reSet() {//重置搜索框
      this.pagination.name = "";
      this.load();
    },
    handleDelete(row) {//删除
      this.$confirm("此操作将永久删除当前信息，是否继续？", "提示", {type: "info"}).then(() => {
        this.request.delete("/role/" + row.id).then(resp => {
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
    selectMenu(row) {

      this.roleId = row.id;
      this.roleFlag = row.role;
      this.request.get("/menu").then(resp => {
        this.menuData = resp.data;

        //把后台返回的菜单数据处理成id数组
        this.expends = this.menuData.map(v => v.id)
      });

      this.request.get("/role/roleMenu/" + this.roleId).then(resp => {
        this.checks = resp.data;
      });


      this.request.get("/menu/ids").then(resp => {
        const ids = resp.data;

        ids.forEach(id => {
          if (!this.checks.includes(id)) {//取消选中
            /*由于选中父级菜单 其子级菜单均会被设置为checked
             因此需要过滤一下
              经过查询如果数据库表中选中的子id并未包含在全选集中 则设为unchecked
              */
            this.$nextTick(() => { //使用nextTick处理未来元素 等到这个元素渲染完成后才去使用
              this.$refs.tree.setChecked(id, false);
            })
          }
        })
        this.menuDialogVis = true;
      })
    },
    handleSelectionChange(val) { //多选控制

      this.multipleSelection = val;
    },
    delBatch() {//批量删除
      this.$confirm("此操作将永久删除当前信息，是否继续？", "提示", {type: "info"}).then(() => {
        //判断操作是否成功
        let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
        this.request.post("/role/del/batch", ids).then(res => {
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
    }
  }
}
</script>

<style scoped>
.headerBg {
  background: #eee !important;
}
</style>
