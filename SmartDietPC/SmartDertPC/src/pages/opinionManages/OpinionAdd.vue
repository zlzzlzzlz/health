<template>
  <div class="userDialog">
    <el-dialog width="400px" title :visible.sync="dialogAdd" :close-on-click-modal="true">
      <el-form
        :model="addInfo"
        label-width="100px"
        :rules="rules"
        ref="addInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="姓名" prop="userName" label-width="110px">
          <el-input v-model="addInfo.userName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" label-width="110px">
          <el-input v-model="addInfo.sex" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard" label-width="110px">
          <el-input v-model="addInfo.idCard" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone" label-width="110px">
          <el-input v-model="addInfo.phone" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role" label-width="110px">
          <el-input v-model="addInfo.role" maxlength="40" clearable></el-input>
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
        sex: "",
        idCard: "",
        phone: "",
        role: ""
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
      this.dialogAdd = false;
    }
  }
};
</script>

<style scoped>
</style>