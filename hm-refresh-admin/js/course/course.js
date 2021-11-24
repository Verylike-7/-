// 定义组件
const CourseCourse = Vue.extend({
    template: `
     <div style="padding: 20px">
         <el-card class="operate-container" shadow="never" style="margin-bottom: 10px">
          <i class="el-icon-tickets"></i>
          <span>课程列表</span>
          <el-button size="mini"  @click="handleExport"
              style="margin-left: 20px" >导出</el-button>
              <el-button size="mini" @click="handleImport"
              style="margin-left: 20px">导入</el-button>
         </el-card>
         <div style="display: flex; justify-content: left">
            <el-pagination
                background
                layout="prev, pager, next"
                @current-change="handleCurrentChange"
                :page-size="pageSize"
                :total="total"
                >
              </el-pagination>
          </div>
          <el-table :data="tableData" style="width: 100%"  row-key="id" border header-align="center">
              <el-table-column align="center" prop="cno" label="课程编号" width="520"></el-table-column>
              <el-table-column align="center" prop="cname" label="课程名称" width="520"></el-table-column>
              <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini" type="success" plain
                    @click="handleAdd">新增</el-button>
                  <el-button plain
                    size="mini"
                    type="danger"
                    @click="handleDelete(scope.row.cno)">删除</el-button>
                  <el-button plain
                    size="mini"
                    type="primary"
                    @click="handleEdit(scope.row)">修改</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-dialog 
            title="课程信息" 
            :visible.sync="dialogVisible" 
            width="30%" style="padding: 0 20px;">
            <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="课程编号">
            <el-input v-model="form.cno"></el-input>
            </el-form-item>
            <el-form-item label="课程名称">
            <el-input v-model="form.cname"></el-input>
            </el-form-item>
            </el-form>
          
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmForm">确 定</el-button>
          </div>
         
        </el-dialog>
        <div v-show="false">
          <form id="exportForm" action="http://localhost:8088/course/export" method="get">
           </form>
            <form id="importForm" action="http://localhost:8088/course/import" method="post">
                <input id="fileUpload" type="file" name="file" @change="handleUpload">
           </form>
          </div>
     </div>
    `,
    data() {
        return {
            tableData: [],
            total: 0,//总条数
            pageSize: 5,//每页大小
            dialogVisible: false,
            form: {},
            isEdit: false, //当前新增还是修改
        }
    },
    methods: {
        handleUpload() {
            let inputFile = document.getElementById("fileUpload");
            let file = inputFile.files[0];
            let formData = new FormData();
            formData.append("file", file, file.name);

            const config = {
                headers: {"Content-Type": "multipart/form-data;boundary=" + new Date().getTime()}
            };

            axios.post("/course/import", formData, config)
                .then(function (response) {
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                });

        },
        handleExport() {
            let form = document.getElementById("exportForm");
            form.submit();
        },
        handleImport() {
            let inputFile = document.getElementById("fileUpload");
            inputFile.click();
        },
        handleDelete: function (cno) {
            this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteById(cno);
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        deleteById(cno) {
            axios.delete("/course/" + cno)
                .then(() => {
                    this.queryByPage(1);
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                })
                .catch(err => {
                    this.$message({
                        type: 'error',
                        message: '删除失败!'
                    });
                    console.log(err);
                })
        },
        handleEdit(row) {
            this.form = {
                cno: row.cno,
                cname: row.cname
            };
            //设置为修改
            this.isEdit = true;
            //显示表单
            this.dialogVisible = true;
        },
        handleAdd() {
            //置空表单
            this.form = {};
            //设置为新增
            this.isEdit = false;
            //显示表单
            this.dialogVisible = true;
        },
        confirmForm() {
            //判断新增还是修改
            if (this.isEdit) {
                axios.put("/course/update", this.form)
                    .then(
                        () => {
                            this.dialogVisible = false;
                            this.$message({
                                message: '修改成功',
                                type: 'success'
                            });
                        }
                    )
                    .catch(err => {
                        this.$message({
                            message: '修改失败',
                            type: 'error'
                        });
                        console.log(err)
                    })
            } else {
                axios.post("/course/save", this.form)
                    .then(
                        () => {
                            this.dialogVisible = false;
                            this.queryByPage(1);
                            this.$message({
                                message: '成功添加',
                                type: 'success'
                            });
                        }
                    )
                    .catch(err => {
                        this.$message({
                            message: '添加失败',
                            type: 'error'
                        });
                        console.log(err)
                    })
            }

        },
        handleCurrentChange(page) {
            this.queryByPage(page);
        },
        queryByPage(page) {
            //查后台数据，页面渲染，  /course/all?page=1&pagesize=2
            axios.get("/course/all",
                {
                    params: {
                        page: page,
                        size: this.pageSize
                    }
                }
            )
                .then(resp => {
                    this.tableData = resp.data.items;
                    this.total = resp.data.total;
                })
                .catch(err => {
                    console.log(err);
                })
        }
    },
    created() {
        this.queryByPage(1);
    }
});