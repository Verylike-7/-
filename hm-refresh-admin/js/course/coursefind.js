// 定义组件
const CourseFind = Vue.extend({
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
     </div>
    `,
    data() {
        return {
            tableData: [],
            total: 0,//总条数
            pageSize: 5,//每页大小
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