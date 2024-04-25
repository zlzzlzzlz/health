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
          <el-input
            v-model="editInfo.username"
            maxlength="40"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" label-width="110px">
          <el-input
            v-model="editInfo.password"
            maxlength="40"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="用户身份" label-width="110px" prop="type">
          <el-select v-model="editInfo.type" placeholder="请选择用户身份" clearable maxlength="40">
            <el-option label="普通用户" value="1" maxlength="40"></el-option>
            <el-option label="管理用户" value="0" maxlength="40"></el-option>
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
        username: "",
        password: "",
        type:"",
        id:"",
      },
      userId: "",
      version: "",
      rules: {
        //校验
        //用户名
        username: [
          {
            required: true,
            pattern: /^[\u4E00-\u9FA5A-Za-z]+$/,
            trigger: "blur",
            message: "请输入1-20位中文或英文"
          }
        ],
        //手机号
        password: [
          {
            required: true,
            // pattern: /^1[34578]\d{9}$/,
            // trigger: "blur",
            // message: "请输入正确的手机号码"
          }
        ],
        type: [
          {
            required: true,
            // pattern: /^[1-9]{1}\d{5}[1-9]{2}\d{9}[Xx0-9]{1}$/,
            // trigger: "blur",
            message: "请选择用户身份"
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
        this.editInfo.username = val.username;
        this.editInfo.password = val.password;
        this.editInfo.type = val.type;
        this.editInfo.id = val.id;
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
            type: this.type,
            username: this.editInfo.username,
            password: this.editInfo.password,
          };
          this.$axios.post("prod-api/user/editUser?id="+this.editInfo.id+"&username="+
                this.editInfo.username +
                "&password=" +
                this.editInfo.password +
                "&usertype=" +
                this.editInfo.type).then(res => {
            if (200 == res.status) {
              this.$message({
                showClose: true,
                message: "修改成功",
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