const banner = {
  template: `
    <div class="banner" >
       <el-image class="icon"  src="./images/login_center_bg.png" min-width="100" height="100"></el-image>
        <div class="title">学籍管理系统</div>
        <el-dropdown trigger="hover" >
            <span class="el-dropdown-link">
                <i class="el-icon-menu"></i> 主题
            </span>
            <el-dropdown-menu slot="dropdown" >
                <el-dropdown-item style="font-size: 12px; line-height: 16px; display: flex; align-items: center;">
                <span>背景色&nbsp;</span><el-color-picker size="mini" v-model="util.theme.menuBGC"></el-color-picker>
                </el-dropdown-item>
                <el-dropdown-item style="font-size: 12px; line-height: 16px; display: flex; align-items: center;">
                <span>字体色&nbsp;</span><el-color-picker size="mini" v-model="util.theme.menuColor"></el-color-picker>
                </el-dropdown-item>
            </el-dropdown-menu>
            </el-dropdown>
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link">
              <i class="el-icon-user"></i> {{user.name}}
          </span>
          <el-dropdown-menu slot="dropdown" >
              <el-dropdown-item command="1">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
    </div>
    `,
  data() {
    return {
      util,
      user: {}
    }
  },
  created() {
    // 判断是否登录
    let userId = sessionStorage.userId;
    if (!userId) {
      // 未登录
      location = "/login.html";
    }
    // 获取登录用户，
    axios.get("/user/" + userId)
      .then(resp => {
        this.user = resp.data;
      })
      .catch(err => {
        console.log(err)
      });
  },
  methods: {
    handleCommand(i) {
      this.logout();
    },
    logout(){
      delete sessionStorage.userId;
      location = "/login.html";
    }
  }

}
// 注册顶部banner组件
Vue.component("banner", banner);