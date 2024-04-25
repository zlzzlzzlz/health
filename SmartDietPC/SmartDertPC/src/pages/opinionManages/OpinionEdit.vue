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
        <el-form-item label="姓名" prop="userName" label-width="110px">
          <el-input v-model="editInfo.userName" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex" label-width="110px">
          <el-input v-model="editInfo.sex" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard" label-width="110px">
          <el-input v-model="editInfo.idCard" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone" label-width="110px">
          <el-input v-model="editInfo.phone" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role" label-width="110px">
          <el-input v-model="editInfo.role" maxlength="40" clearable></el-input>
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
        sex: "",
        idCard: "",
        phone: "",
        role: ""
      },
      rules: {
        //校验
        userName: [
          {
            required: true
          }
        ],
        sex: [
          {
            required: true
          }
        ],
        phone: [
          {
            required: true
          }
        ],
        idCard: [
          {
            required: true
          }
        ]
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
        console.log(val.role);
        this.editInfo.role = val.role;
      });
    },
    //关闭dialog
    cancelDialog() {
      this.dialogEdit = false;
    },
    //修改
    editSure() {
      this.dialogEdit = false;
    }
  }
};
</script>

<style scoped>
</style>