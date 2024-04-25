<template>
  <div class="login-page">
    <div class="head">
      <div>
        <!-- <span class="logo-img">SmartDiet</span> -->
        <span class="rectangle"></span>
      </div>
    </div>
    <div class="login-wrap">
      <div class="title">
        <p>智慧饮食后台管理系统</p>
        <h4>SMART DIET BACKGROUND MANGMENT</h4>
      </div>
      <div class="ms-login">
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="0px"
          class="ms-content"
        >
          <el-form-item prop="username">
            <el-input
              v-model="ruleForm.username"
              calss="login-input"
              placeholder="用户名/身份证/手机号"
              size="large"
            >
              <template slot="prepend">
                <img src="../assets/yonghu-2.png" alt />
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input
              ref="password"
              :type="passwordType"
              v-model="ruleForm.password"
              calss="login-input"
              placeholder="请输入密码"
              size="large"
            >
              <template slot="prepend">
                <img src="../assets/mima-2.png" alt />
              </template>
            </el-input>
          </el-form-item>
          <div class="login-btn">
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data: function() {
    var validateUser = (rule, value, callback) => {
      const idCard = /^[1-9]{1}\d{5}[1-9]{2}\d{9}[Xx0-9]{1}$/;
      const phone = /^1[34578]\d{9}$/;
      const userName = /^[a-zA-Z][a-zA-Z0-9]{3,15}$/;
      if (!value) {
        return callback(new Error("请输入用户名"));
      }
      setTimeout(() => {
        if (
          !idCard.test(value) &&
          !phone.test(value) &&
          !userName.test(value)
        ) {
          return callback(new Error("您输入的用户名有误"));
        } else {
          callback();
        }
      }, 100);
    };
    return {
      checked: true,
      passwordType: "password",
      ruleForm: {
        username: "admin",
        password: "admin",
        imageCode: "",
        deviceId: "",
        appType: "pc"
      },
      imgurl: "",
      userType: "",
      deviceId: "",
      rules: {
        username: [
          {
            required: true,
            validator: validateUser,
            trigger: "blur"
          }
        ],
        password: [
          {
            required: true,
            message: "请输入密码",
            trigger: "blur"
          }
        ],
        type:"",
      }
    };
  },
  methods: {
    //获取登录人信息
    loginpPerson() {
      this.$axios.get("prod-api/user/GetloginPerson"+
              "?username=" +
              paramForm.username +
              "&password=" +
              paramForm.password).then(res => {
      });
    },

    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let paramForm = {
            username: this.ruleForm.username,
            password: this.ruleForm.password,
            userType: this.userType
          };
          this.$axios.post("prod-api/user/login" +
              "?username=" +
              paramForm.username +
              "&password=" +
              paramForm.password).then(res => {
            if ("success" == res.data) {
              //判断是否为管理员
              this.$axios.get("prod-api/user/GetloginPerson"+
              "?username=" +
              paramForm.username +
              "&password=" +
              paramForm.password).then(res => {
                if(res.data.usertype==0){
                this.$message({
                showClose: true,
                message: "登陆成功",
                type: "success"
              });
              this.$router.push("/home/userManages");
              }
              else{
                this.$message({
                showClose: true,
                message: "请使用管理员账号登录",
                type: "error"
              });
              }
              });
              // this.loginpPerson();
              // localStorage.setItem("token", res.data.token);
              
            } else {
              // this.ruleForm = "";
              this.$message({
                showClose: true,
                message: "登录失败",
                type: "error"
              });
            }
          });
        }
      });
    }
  },
  name: "Login"
};
</script>

<style lang="scss" scoped>
html {
  margin: 0;
  padding: 0;
}
.login-page {
  position: absolute;
  width: 100%;
  height: 850px;
  top: 0px;
  bottom: 0px;
  background: url(../assets/bg77.jpg);
  .head {
    position: relative;
    height: 80px;
    line-height: 80px;
    padding-left: 80px;
    color: #666;
    font-weight: bolder;
    .logo-img {
      position: absolute;
      top: 25px;
      left: 0;
      font-size: 40px;
      color: white;
      width: 118px;
      height: 30px;
      display: inline-block;
      background-size: 100%;
    }
    .rectangle {
      position: absolute;
      left: 213px;
      width: 2px;
      height: 32px;
      display: inline-block;
      background-size: 100%;
    }
  }
  .login-wrap {
    position: absolute;
    width: 70%;
    height: 80%;
    background-position: center center;
    margin-left: 8%;
  }
  .title {
    position: absolute;
    margin-left: 20%;
    top: 25%;
    width: 600px;
    height: 310px;
    background: rgba(192, 191, 191, 0.2);
    color: rgba(255, 255, 255, 0.9);
    padding-left: 20px;
    text-align: left;
    border-radius: 10px 0 0 10px;
    p {
      font-size: 25px;
      letter-spacing: 2px;
      margin-top: 58px;
      color: white;
      text-align: left;
    }
  }
  .mstitle {
    position: absolute;
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    border-bottom: 1px solid #ddd;
  }
  .ms-login {
    position: absolute;
    margin-left: 60%;
    top: 25%;
    width: 430px;
    height: 310px;
    border-radius: 5px;
    background: #fff;
    overflow: hidden;
  }
  .login-input {
    width: 280px;
  }
  .user-name {
    width: 14px;
    height: 16px;
    display: inline-block;
  }
  .ms-content {
    padding: 40px 40px;
  }
  .code-width {
    width: 60%;
  }
  .code-img-width {
    width: 30%;
    height: 32px;
    margin-left: 5%;
    vertical-align: bottom;
  }

  .login-btn {
    text-align: center;
    margin-top: 25px;
  }
  .login-btn button {
    width: 100%;
    height: 45px;
    margin-bottom: 10px;
    font-size: 14px;
  }
}
</style>