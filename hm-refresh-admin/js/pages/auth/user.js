// 定义组件
const AuthUser = Vue.extend({
  template: `
    <div style="padding: 20px">
     <el-card class="operate-container" shadow="never" style="margin-bottom: 10px">
      <i class="el-icon-tickets"></i>
      <span>用户列表</span>
    </el-card>
      <div style="display: flex; justify-content: right">
            <el-pagination
                background
                layout="prev, pager, next"
                @current-change="handleCurrentChange"
                :page-size="pageSize"
                :total="total">
              </el-pagination>
      </div>
      <el-table header-align="center"
          :data="tableData"
          style="width: 100%; margin-top: 10px"
          row-key="id"
          border>
          <el-table-column align="center" prop="id" label="编号" width="120"></el-table-column>
          <el-table-column align="center" prop="name" label="名字" width="150"></el-table-column>
          <el-table-column align="center" prop="username" label="用户名" width="150"></el-table-column>
          <el-table-column align="center" prop="createTime" label="创建时间" width="250"></el-table-column>
          <el-table-column align="center" prop="lastLoginTime" label="最近登录时间" width="250">
            <template slot-scope="scope">
                {{scope.row.lastLoginTime || "NAN"}}
              </template>
          </el-table-column>
          <el-table-column align="center" prop="enable" label="是否启用" width="100" @click="userId = scope.row.id">
            <template slot-scope="scope">
              <el-switch
                :active-value="scope.row.id + '/true'"
                :inactive-value="scope.row.id + '/false'"
                @change="onStateChange"
                v-model="scope.row.enable"
                active-color="#13ce66"
                inactive-color="#ff4949">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini" type="primary" plain
                @click="handleRoleAuth(scope.$index, scope.row)">分配角色</el-button>
              <el-button plain
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
   
        <el-dialog title="用户角色信息" :visible.sync="formVisible" width="30%" style="padding: 0 20px;">
          <el-select v-model="roleIds" multiple placeholder="请选择">
            <el-option
              v-for="item in roles"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <div slot="footer" class="dialog-footer">
            <el-button @click="formVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmForm">确 定</el-button>
          </div>
        </el-dialog>
    </div>
     
    `,
  data() {
    return {
      tableData: [], //菜单列表
      total: 0, // 总条数
      item: {}, // 表单参数
      roleIds: [], // 用户的角色id
      userId: 0, // 用户id
      roles: [], // 所有的可选角色
      formVisible: false, // 是否显示编辑框
      isEdit: false, // 是否是编辑
      formLabelWidth: "100px", // 表单label宽度
      pageSize: 5,
    }
  },
  created() {
    this.init(1);
    this.queryAllRoles();
  },
  methods: {
    handleCurrentChange(page) {
      this.init(page);
    },
    handleDelete(i, r) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteById(r.id);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleAdd(i, r) {
      this.isEdit = false;
      this.item = {};
      this.formVisible = true;
    },
    deleteById(id) {
      axios.delete("/user/" + id)
          .then(() => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            util.reload(this);
          })
          .catch(err => {
            this.$message({
              type: 'error',
              message: '删除失败!'
            });
            console.log(err);
          })
    },
    onStateChange(state) {
      axios.put("/user/state/" + state)
        .then(() => {
          this.$message({
            type: 'success',
            message: '更新成功!'
          });
        })
        .catch(err => {
          this.$message({
            type: 'error',
            message: '更新失败!'
          });
          console.log(err);
        })
    },
    init(page) {
      axios.get("/user/page", {
          params: {
            page, size: 5
          }
        })
        .then(resp => {
          resp.data.items.forEach(u => u.enable = u.id + "/" + u.enable)
          this.tableData = resp.data.items;
          this.total = resp.data.total;
        })
        .catch(err => {
          this.$message({
            message: '查询失败',
            type: 'error'
          });
          console.log(err)
        });
    },
    queryAllRoles() {
      axios.get("/role/all")
        .then(resp => {
          this.roles = resp.data;
        })
        .catch(err => {
          this.$message({
            message: '查询角色失败',
            type: 'error'
          });
          console.log(err)
        });
    },
    handleRoleAuth(i, r) {
      this.userId = r.id;
      // 查询用户角色
      axios.get("/user/role/" + r.id)
        .then(resp => {
          this.roleIds = resp.data;
          this.formVisible = true;
        })
        .catch(err => {
          this.$message({
            message: '查询用户角色失败',
            type: 'error'
          });
          console.log(err)
        });
    },
    confirmForm() {
      // 更新用户角色
      axios.put("/user/role", {id: this.userId, roleIds: this.roleIds})
        .then(() => {
          this.formVisible = false;
          this.$message({
            message: '更新角色成功',
            type: 'success'
          });
        })
        .catch(err => {
          this.$message({
            message: '更新角色失败',
            type: 'error'
          });
          console.log(err)
        });
    }
  }
});