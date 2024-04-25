<template>
  <div class="userDialog">
    <el-dialog width="400px" title :visible.sync="dialogEdit" :close-on-click-modal="true">
      <el-form
        :model="editInfo"
        label-width="100px"
        :rules="rules"
        ref="editInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="用户名" prop="userName" label-width="110px">
          <el-input v-model="editInfo.userName" maxlength="40" readonly clearable></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name" label-width="110px">
          <el-input v-model="editInfo.name" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" label-width="110px">
          <el-input v-model="editInfo.email" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone" label-width="110px">
          <el-input v-model="editInfo.phone" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="editInfo.sex" placeholder="请选择性别">
            <el-option label="男" value="0"></el-option>
            <el-option label="女" value="1"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="editSure('editInfo')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "editData",
  data() {
    return {
      dialogEdit: false,
      editInfo: {
        userName: "",
        name: "",
        phone: "",
        sex: "",
        email: ""
      },
      rules: {
        //校验
        //用户名
        userName: [
          {
            required: true,
            pattern: "",
            trigger: "blur",
            message: ""
          }
        ],
        sex: [
          {
            required: true,
            pattern: /^(男|女)$/,
            trigger: "blur",
            message: "请输入您的性别"
          }
        ],
        //手机号
        phone: [
          {
            required: true,
            pattern: /^1[34578]\d{9}$/,
            trigger: "blur",
            message: "请输入正确的手机号码"
          }
        ],
        idCard: [
          {
            required: true,
            pattern: /^[1-9]{1}\d{5}[1-9]{2}\d{9}[Xx0-9]{1}$/,
            trigger: "blur",
            message: "请输入正确的身份证号码"
          }
        ]
        //身份证
      }
    };
  },
  mounted() {},
  methods: {
    //打开dialog
    openDialog(flag, val) {
      this.dialogEdit = flag;
      this.$nextTick(() => {
        this.$refs.editInfo.resetFields();
        console.log(val);
        this.editInfo.userName = val.userName;
        this.editInfo.name = val.nickName;
        this.editInfo.sex = val.sex;
        this.editInfo.email = val.email;
        this.editInfo.phone = val.phonenumber;
        this.userId = val.userId;
        this.version = val.version;
      });
    },
    //关闭dialog
    cancelDialog() {
      this.dialogEdit = false;
    },
    //修改
    editSure() {
      this.dialogEdit = false;
      this.$refs["editInfo"].validate(valid => {
        if (valid) {
          let pamars = {
            userId: this.userId,
            userName: this.editInfo.userName,
            nickName: this.editInfo.name,
            phonenumber: this.editInfo.phone,
            email: this.editInfo.email,
            sex: this.editInfo.sex,
            version: this.version
          };
          this.$axios
            .put("prod-api/backend/customer/update", pamars)
            .then(res => {
              if (200 == res.data.code) {
                this.$message({
                  showClose: true,
                  message: res.data.meg,
                  type: "success"
                });
                this.$emit("search");
              }
            });
        }
      });
    }
  }
};
</script>

<style scoped>
</style>