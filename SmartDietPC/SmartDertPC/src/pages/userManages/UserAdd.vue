<template>
  <div class="userDialog">
    <el-dialog
      width="400px"
      title
      :visible.sync="dialogAdd"
      :close-on-click-modal="true"
    >
      <el-form
        :model="addInfo"
        label-width="100px"
        :rules="rules"
        ref="addInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="用户名" prop="userName" label-width="110px">
          <el-input
            v-model="addInfo.userName"
            maxlength="40"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" label-width="110px">
          <el-input
            v-model="addInfo.password"
            maxlength="40"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="用户身份" label-width="110px" prop="type">
          <el-select v-model="addInfo.type" placeholder="请选择用户身份" clearable maxlength="40">
            <el-option label="普通用户" value="1" maxlength="40"></el-option>
            <el-option label="管理用户" value="0" maxlength="40"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="addSure('addInfo')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "addData",
  data() {
    return {
      dialogAdd: false,
      addInfo: {
        userName: "",
        password: "",
        type:"",
      },
      rules: {
        //校验
        //用户名
        userName: [
          {
            required: true,
            pattern: /^[\u4E00-\u9FA5A-Za-z]+$/,
            trigger: "blur",
            message: "请输入1-20位中文或英文"
          },
        ],
        password: [
          {
            required: true,
          },
        ],
        type: [
          {
            required: true,
          },
        ],
        email: [
          {
            required: true,
          },
        ],
        //手机号
        phone: [
          {
            required: true,
            pattern: /^1[34578]\d{9}$/,
            trigger: "blur",
            message: "请输入正确的手机号码",
          },
        ],
        // idCard: [
        //   {
        //     required: true,
        //     pattern: /^[1-9]{1}\d{5}[1-9]{2}\d{9}[Xx0-9]{1}$/,
        //     trigger: "blur",
        //     message: "请输入正确的身份证号码"
        //   }
        // ]
        //身份证
      },
    };
  },
  mounted() {},
  methods: {
    //打开dialog
    openDialog(flag, val) {
      this.dialogAdd = flag;
      this.$nextTick(() => {
        this.$refs.addInfo.resetFields();
      });
    },
    //关闭dialog
    cancelDialog() {
      this.dialogAdd = false;
    },
    //新增
    addSure() {
      //this.dialogAdd = false;
      this.$refs["addInfo"].validate((valid) => {
        if (valid) {
          //this.loading.startLoading();
          let pamars = {
            userName: this.addInfo.userName,
            password: this.addInfo.password,
            type:this.addInfo.type,
          };
          this.$axios
            .post(
              "prod-api/user/addUser?username=" +
                this.addInfo.userName +
                "&password=" +
                this.addInfo.password +
                "&usertype=" +
                this.addInfo.type
            )
            .then((res) => {
              //this.loading.endLoading();
              if (200 == res.status) {
                this.$message({
                  showClose: true,
                  message: "增加用户成功",
                  type: "success",
                });
                this.$emit("search");
                this.cancelDialog();
              } else {
                this.$message({
                  showClose: true,
                  message: res.data.msg,
                  type: "error",
                });
              }
            });
        }
      });
    },
  },
};
</script>

<style scoped>
</style>