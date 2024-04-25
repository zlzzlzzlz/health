<template>
  <div class="all5">
    <div class="topimg">
      <img src="../assets/bg1.jpg" style="height:200px;width:100%;margin-top:-60px;" />
      <img src="../assets/logo2.jpg" style="position: relative;top: -170px;" />
    </div>
    <div class="form1" :model="ruleForm" :rules="rules" ref="ruleForm">
      <van-form>
        <van-field
          prop="username"
          v-model="ruleForm.username"
          name="用户名"
          placeholder="用户名"
          left-icon="manager"
          style="left:40px;width:350px;height:60px"
        />
        <van-field
          prop="password"
          v-model="ruleForm.password"
          type="password"
          name="密码"
          placeholder="密码"
          left-icon="eye"
          style="left:40px;width:350px"
        />
        <div style="margin: 32px;">
          <van-button round block plain type="primary" native-type="submit" @click="toLogin">登 录</van-button>
        </div>
      </van-form>
    </div>
    <a
      href="#"
      style="color: grey;
    position: absolute;
    margin-left: -170px;
    margin-top: -60px;"
      @click="forget()"
    >忘记密码</a>
    <a
      @click="toregist"
      style="color: grey;
    position: absolute;
    margin-left: 100px;
    margin-top: -60px;"
    >注册</a>
  </div>
</template>

<script>
import { Toast } from "vant";
export default {
  name: "Login",
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
        username: "renjiahui",
        password: "renjiahui12"
      },
      imgurl: "",
      userType: "02",
      deviceId: "",
      // img: require("../assets/food/rice.png"),
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
        ]
      }
    };
  },
  methods: {
    toregist() {
      this.$router.push("registered");
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let paramForm = {
            username: this.ruleForm.username,
            password: this.ruleForm.password
          };
          this.$axios.post("prod-api/user/login", paramForm).then(res => {
            if (200 == res.data.code) {
              localStorage.setItem("token", res.data.token);
              console.log(re.data.token);
              this.$toast.success("登陆成功");
              this.$router.push("home");
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: "success"
              });
            } else {
              // this.ruleForm = "";
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
    // roregist() {
    //   this.$router.push({ path: "/regist" });
    // },
    toLogin() {
      //用户名正则，4到16位（字母，数字，下划线，减号）
      var uPattern = /^[a-zA-Z0-9_-]{4,16}$/;
      //密码至少包含 数字和英文，长度6-20
      var passwordcheck = /^[a-zA-Z0-9_-]{4,16}$/;
      var data = {
        userAccount: this.ruleForm.username,
        password: this.ruleForm.password
      };
      if ("" == this.username) {
        this.$toast.fail("请输入用户名");
      } else if ("" == this.password) {
        this.$toast.fail("请输入密码");
      } else if (!uPattern.test(this.username)) {
        this.$toast.fail("用户名应为4到16位");
        this.username = "";
        return;
      } else if (!passwordcheck.test(this.password)) {
        this.$toast.fail("密码至少包含 数字和英文，长度6-20");
        this.password = "";
      } else {
        var paramForm = {
          username: this.ruleForm.username,
          password: this.ruleForm.password
        };
        this.$axios
          .post(
            "prod-api/user/login" +
              "?username=" +
              paramForm.username +
              "&password=" +
              paramForm.password
          )
          .then(res => {
            console.log(res);
            if ("success" == res.data) {
              this.$axios
                .get(
                  "prod-api/user/GetloginPerson" +
                    "?username=" +
                    paramForm.username +
                    "&password=" +
                    paramForm.password
                )
                .then(res => {
                  console.log(res.data.id);
                  localStorage.setItem("id", res.data.id);
                });
              // this.loginpPerson();
              // localStorage.setItem("token", res.data.token);
              this.$toast.success("登录成功");
              localStorage.setItem("username", paramForm.username);
              this.$router.push({ path: "/home" });
            } else {
              this.$toast.fail("登录失败");
            }
          });
      }
    },
    forget() {
      Toast({
        message: "请联系管理员"
      });
    }
  }
};
</script>

<style scoped>
.all5 {
  background: white;
  height: 724px;
}
.form1 {
  position: relative;
  top: -80px;
}
.van-cell {
  color: #02e2c4;
}
</style>
<style >
/* .van-nav-bar__text {
  color: white;
}
.van-nav-bar .van-icon {
  color: white;
}
.van-nav-bar__title {
  max-width: 60%;
  margin: 0 auto;
  color: white;
  font-weight: 500;
  font-size: 16px;
} */
</style>