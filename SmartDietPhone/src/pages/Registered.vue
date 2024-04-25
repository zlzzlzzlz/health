<template>
  <div class="reg">
    <div class="vanNav">
      <van-nav-bar title="注册" left-text="返回" left-arrow @click-left="handleClick" fixed />
    </div>
    <br />
    <br />
    <div class="reg1">
      <!-- <img class="logo1" src="../assets/logo1 copy.png" /> -->
      <span class="eng">智慧饮食</span>
    </div>
    <van-field
      style="left:40px;width:260px "
      v-model="userName"
      left-icon="manager"
      name="用户名"
      placeholder="请输入用户名"
    />
    <!-- 输入密码 -->
    <van-field
      v-model="password"
      type="password"
      style="left:40px;width:260px "
      left-icon="eye"
      name="密码"
      placeholder="请输入密码"
    />
    <div class="input_span">
      <label style="margin-left:52px;margin-top:10px;"></label>
      <span id="one"></span>
      <span id="two"></span>
      <span id="three"></span>
    </div>
    <div id="font" style="margin-left:30px;">
      <span>弱</span>
      <span>中</span>
      <span>强</span>
    </div>
    <van-field
      v-model="repassword"
      type="password"
      style="left:40px;width:260px "
      left-icon="eye"
      name="确认密码"
      placeholder="请确认密码"
    />
    <!-- 输入手机号-->
    <!-- <van-field
      v-model="phonenumber"
      type="digit"
      style="left:40px;width:260px "
      left-icon="phone"
      name="手机号"
      placeholder="请输入手机号"
    />
    <van-field
      v-model="nickName"
      style="left:40px;width:260px "
      left-icon="contact"
      name="姓名"
      placeholder="请输入姓名"
    />
    <van-field
      v-model="email"
      style="left:40px;width:260px "
      left-icon="free-postage"
      name="邮箱"
      placeholder="请输入邮箱"
    /> -->
    <!-- <van-field name="radio" label="性别" style="left:40px;width:260px ">
      <template #input>
        <van-radio-group v-model="sex" direction="horizontal">
          <van-radio name="0">男</van-radio>
          <van-radio name="1">女</van-radio>
        </van-radio-group>
      </template>
    </van-field>  -->
    <br />
    <br />
    <van-button
      round
      block
      type="info"
      native-type="submit"
      style="width:300px;left:40px"
      @click="toLogin"
    >注册</van-button>
  </div>
</template>

<script>
import { Toast } from "vant";
export default {
  data() {
    return {
      phonenumber: "",
      sex: "",
      userName: "",
      nickName: "",
      email: "",
      password: "",
      repassword: "",
      msgText: ""
    };
  },
  methods: {
    toLogin() {
      var re = /^[a-zA-Z0-9_-]{3,15}$/;
      var passwordcheck = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
      var email = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      var tel = /^[0-9]{7,11}$/;
      var name = /^([\u4E00-\u9FFF]|\w){1,10}$/;
      var data = {
        userAccount: this.userName,
        password: this.password
      };
      if ("" == this.userName) {
        this.$toast.fail("请输入用户名");
      } else if (!re.test(this.userName)) {
        this.$toast.fail("用户名应为4到16位");
        this.username = "";
        return;
      } else if ("" == this.password) {
        this.$toast.fail("请输入密码");
      } else if (!passwordcheck.test(this.password)) {
        this.$toast.fail("密码至少包含 数字和英文，长度为6-20");
        this.password = "";
      } else if ("" == this.repassword) {
        this.$toast.fail("请输入确认密码");
      } else if (this.password != this.repassword) {
        this.$toast.fail("两次密码输入不一致，请重新输入");
        this.repassword = "";
      }  else {
        var paramForm = {
          userName: this.userName,
          password: this.password,
        };
        this.$axios.post("prod-api/user/addUser?username="+this.userName+"&password="+this.password+"&usertype=1").then(res => {
          if (200 == res.status) {
            this.$toast.success("注册成功");
            this.$router.push({ path: "/" });
          } else {
            this.$toast.fail(res.data.msg);
          }
        });
      }
    },
    handleClick() {
      this.$router.push("/");
    },
    checkStrong(sValue) {
      var modes = 0;
      //正则表达式验证符合要求的
      if (sValue.length < 1) return modes;
      if (/\d/.test(sValue)) modes++; //数字
      if (/[a-z]/.test(sValue)) modes++; //小写
      if (/[A-Z]/.test(sValue)) modes++; //大写
      if (/\W/.test(sValue)) modes++; //特殊字符

      //逻辑处理
      switch (modes) {
        case 1:
          return 1;
          break;
        case 2:
          return 2;
          break;
        case 3:
        case 4:
          return sValue.length < 4 ? 3 : 4;
          break;
      }
      return modes;
    }
  },
  components: {},
  watch: {
    password(newname, oldname) {
      this.msgText = this.checkStrong(newname);
      if (this.msgText > 1 || this.msgText == 1) {
        document.getElementById("one").style.background = "red";
      } else {
        document.getElementById("one").style.background = "#eee";
      }
      if (this.msgText > 2 || this.msgText == 2) {
        document.getElementById("two").style.background = "orange";
      } else {
        document.getElementById("two").style.background = "#eee";
      }
      if (this.msgText == 4) {
        document.getElementById("three").style.background = "#00D1B2";
      } else {
        document.getElementById("three").style.background = "#eee";
      }
    }
  }
};
</script>

<style scoped>
.reg {
  position: relative;
  margin: 0 auto;
  border: 1px solid #e3e3e4;
  background-color: white;
  height: 812px;
  width: 375px;
}

.van-cell {
  color: #046ac6;
}
.reg1 {
  margin: 0 0 0 40px;
}
.logo1 {
  width: 300px;
}

.eng {
  position: absolute;
  top: 100px;
  left: 70px;
  color: rgb(204, 194, 194);
  font-weight: 900;
  font-size: 24px;
  font-family: "幼圆";
}
.sex {
  width: 300px;
  height: 35px;
  cursor: pointer;
  text-indent: 54px;
  border: none;
  line-height: 35px;
  -moz-appearance: none;
}
#inputValue {
  width: 240px;
  margin-left: 20px;
  padding-left: 10px;
  border-radius: 3px;
}
.input_span span {
  display: inline-block;
  width: 85px;
  height: 10px;
  background: #eee;
  line-height: 20px;
}

#one {
  border-top-left-radius: 5px;
  border-bottom-left-radius: 5px;
  border-right: 0px solid;
  margin-left: 20px;
  margin-right: 3px;
}

#two {
  border-left: 0px solid;
  border-right: 0px solid;
  margin-left: -5px;
  margin-right: 3px;
}

#three {
  border-top-right-radius: 5px;
  border-bottom-right-radius: 5px;
  border-left: 0px solid;
  margin-left: -5px;
}
#font span:nth-child(1) {
  color: red;
  margin-left: 80px;
}
#font span:nth-child(2) {
  color: orange;
  margin: 0 60px;
}
#font span:nth-child(3) {
  color: #00d1b2;
}
</style>
