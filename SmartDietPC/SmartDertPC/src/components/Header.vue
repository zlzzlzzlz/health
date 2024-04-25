<template>
  <div class="header">
    <div class="systemTitle">智慧饮食后台管理系统</div>
    <div class="systemTitle1" @click="toUser">用户管理</div>
    <div class="systemTitle2" @click="tofood">菜品管理</div>
    <div class="systemTitle4" @click="toAdvice">反馈管理</div>
    <div class="systemTitle3" @click="toAd">文章管理</div>
    <div class="systemTitle5" @click="toEat">食谱管理</div>
    <div class="header-right">
      <div class="header-user-con">
        <!-- <div class="user-avator">
          <img src="../assets/pic.png" />
        </div> -->
        <!-- <div class="name">Admin</div> -->
        <!--下拉菜单-->
        <!-- <el-dropdown class="user-name" trigger="click" @command="handleCommand">
          <span class="el-dropdown-link">
            <i>
              <img src="../assets/123.png" />
            </i>
          </span>
          <!--下拉框-->
          <!-- <el-dropdown-menu slot="dropdown">
            <el-dropdown-item divided command="changePwd">修改密码</el-dropdown-item>
            <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
          </el-dropdown-menu> -->
        <!-- </el-dropdown> --> 
      </div>
    </div>

    <el-dialog
      title="修改密码"
      :visible.sync="dialogPwdEdit"
      width="30%"
      :close-on-click-model="false"
      :before-close="cancelDialog"
    >
      <el-form
        :model="editPwd"
        :rules="rules"
        ref="editPwd"
        label-width="100px"
        label-position="right"
        class="changePassword"
      >
        <!--弹出的模态框-->
        <el-form-item label="原密码" prop="passWord">
          <el-input ref="rePassword" :type="rePassword" v-model="editPwd.rePassword"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="passWord">
          <el-input ref="password" :type="passwordType" v-model="editPwd.password"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取消</el-button>
        <el-button type="primary" @click="editPwdSure()">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Header",
  components: {},

  data() {
    /*校验是否全由8位数字组成 */
    // function mima(str) {
    //   // var reg = /^[0-9A-Za-z]{1,16}$/; /*定义验证表达式*/
    //   // return reg.test(str); /*进行验证*/
    // }
    //密码校验
    // var validatePassword = (rule, value, callback) => {
    // if (value === "") {
    //   callback(new Error("请输入密码"));
    // }
    // if (mima(value) == false) {
    //   callback(new Error("密码只能是1-16位字母或数字"));
    // } else {
    //   if (this.editPwd.passwordCheck !== "") {
    //     this.$refs.editPwd.validateField("passwordCheck");
    //   }
    //   callback();
    // }
    // };
    //密码确认
    // var validatePassWordCheck = (rule, value, callback) => {
    //   // if (value === "") {
    //   //   callback(new Error("请再次输入密码"));
    //   // } else if (value !== this.editPwd.passWord) {
    //   //   callback(new Error("两次输入密码不一致!"));
    //   // } else {
    //   //   callback();
    //   // }
    // };
    return {
      collapse: true,
      message: 2,
      editPwd: {
        passWord: "",
        rePassword: ""
      },
      dialogPwdEdit: false,
      passwordType: "password",
      rePassword: "password",
      token: "",
      userName: "",
      //修改密码
      rules: {
        // repassword: [{ required: true }],
        // passWord: [{ required: true }]
      }
    };
  },

  methods: {
    toUser(){
      this.$router.push("/home/userManages")
    },
    toAdvice(){
      this.$router.push("/home/opinionManages")
    },
    tofood(){
      this.$router.push("/home/customerManages")
    },
    toAd(){
      this.$router.push("/home/adManages")
    },
    toEat(){
      this.$router.push("/home/boxManages")
    },
    //用户下拉菜单
    handleCommand(command) {
      if (command == "changePwd") {
        //显示模态框
        this.dialogPwdEdit = true;
      } else {
        //退出登录
        this.$axios.post("prod-api/common/logout").then(res => {
          //this.loading.endLoading();
          if (200 == res.data.code) {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: "success"
            });
            localStorage.setItem("userName", "");
            this.$router.push({
              path: "/"
            });
          } else {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: "error"
            });
          }
        });
      }
    },
    editPwdSure() {
      this.dialogPwdEdit = false;
      this.$refs["editPwd"].validate(valid => {
        if (valid) {
          this.$axios
            .put(
              "prod-api/backend/home/updatePassword/" +
                this.editPwd.rePassword +
                "/" +
                this.editPwd.password
            )
            .then(res => {
              if (0 == res.data.code) {
                this.$message({
                  showClose: true,
                  message: res.data.msg,
                  type: "success"
                });
                this.cancelDialog();
              } else {
                this.$message({
                  showClose: true,
                  message: res.data.msg,
                  type: "error"
                });
              }
            });
        }
      });
    },
    //取消菜单
    cancelDialog() {
      this.passwordType = "password";
      this.passwordCheck = "password";
      this.dialogPwdEdit = false;
      this.$refs["editPwd"].resetFields(); //表单重置
    }
  },
  mounted() {
    this.userName = localStorage.getItem("userName");
  }
};
</script>

<style lang="scss" scoped>
.header {
  position: relative;
  box-sizing: border-box;
  width: 100%;
  height: 60px;
  top: -50px;
  font-size: 22px;
  border-bottom: 1px solid #dadfe7;
  background: #fff;
  .changePassword {
    .el-form-item {
      position: relative;
    }
    .el-input {
      width: 90%;
    }
    .show-pwd {
      position: absolute;
      right: 13%;
      top: 2px;
      font-size: 16px;
      cursor: pointer;
      user-select: none;
    }
  }
  .handle-box {
    margin-top: 10px;
  }
  .hamburger-container {
    line-height: 56px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }
  .systemTitle {
    float: left;
    line-height: 60px;
    margin-left: 70px;
    color: #02e2c4;
    font-weight: bold;
  }
  .systemTitle1{
    float: left;
    line-height: 60px;
    margin-left: 480px;
    color: 02e2c4;
    font-weight: 15px;
    font-size: 17px;
  }
  .systemTitle1:hover{
    color:greenyellow;
    cursor:pointer;
  }
   .systemTitle2:hover{
    color:greenyellow;
    cursor:pointer;
  }
   .systemTitle3:hover{
    color:greenyellow;
    cursor:pointer;
  }
  .systemTitle4:hover{
    color:greenyellow;
    cursor:pointer;
  }
  .systemTitle5:hover{
    color:greenyellow;
    cursor:pointer;
  }
    .systemTitle2{
    float: left;
    line-height: 60px;
    margin-left: 70px;
    color: black;
    font-weight: 15px;
    font-size: 17px;
  }
    .systemTitle3{
    float: left;
    line-height: 60px;
    margin-left: 70px;
    color: black;
    font-weight: 15px;
    font-size: 17px;
  }
  .systemTitle4{
    float: left;
    line-height: 60px;
    margin-left: 70px;
    color: black;
    font-weight: 15px;
    font-size: 17px;
  }
  .systemTitle5{
    float: left;
    line-height: 60px;
    margin-left: 70px;
    color: black;
    font-weight: 15px;
    font-size: 17px;
  }
  .user-name {
    top: -40px;
    margin-left: 10px;
  }
  .user-avator {
    margin-top: -70px;
    margin-left: 20px;
  }
  .user-avator img {
    display: block;
    width: 50px;
    height: 50px;
    border-radius: 50%;
  }
  .header-right {
    float: right;
    padding-right: 50px;
  }
  .header-user-con {
    display: flex;
    padding: 60px;
    align-items: center;
  }
  .el-dropodown-link {
    color: #aaa;
    cursor: pointer;
  }
  .el-dropdown-menu__item {
    text-align: center;
  }
  .name {
    color: black;
    position: relative;
    z-index: 1;
    top: -38px;
    font-size: 15px;
  }
}
</style>