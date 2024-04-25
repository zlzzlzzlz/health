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
        <el-form-item label="订单编号" prop="orderCode" label-width="110px">
          <el-input v-model="editInfo.orderCode" maxlength="40" readonly clearable></el-input>
        </el-form-item>
        <el-form-item label="订单状态" prop="sta" label-width="110px">
          <el-input v-model="editInfo.sta" maxlength="40" readonly clearable></el-input>
        </el-form-item>
        <el-form-item label="订单名称" prop="orderName" label-width="110px">
          <el-input v-model="editInfo.orderName" maxlength="40" readonly clearable></el-input>
        </el-form-item>
        <el-form-item label="客户名称" prop="nickName" label-width="110px">
          <el-input v-model="editInfo.nickName" maxlength="40" readonly clearable></el-input>
        </el-form-item>
        <el-form-item label="箱子id" prop="boxId" label-width="110px">
          <el-input v-model="editInfo.boxId" maxlength="40" readonly clearable></el-input>
        </el-form-item>
        <el-form-item label="箱子编号" prop="boxCode" label-width="110px">
          <el-input v-model="editInfo.boxCode" maxlength="40" readonly clearable></el-input>
        </el-form-item>
        <el-form-item label="所需积分" prop="boxUnitPrice" label-width="110px">
          <el-input v-model="editInfo.boxUnitPrice" maxlength="40" readonly clearable></el-input>
        </el-form-item>
        <el-form-item label="租赁时长" prop="leaseDuration" label-width="110px">
          <el-input v-model="editInfo.leaseDuration" maxlength="40" readonly clearable></el-input>
        </el-form-item>
        <el-form-item label="实付积分" prop="totalPrice" label-width="110px">
          <el-input v-model="editInfo.totalPrice" maxlength="40" readonly clearable></el-input>
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
        email: "",
        boxCode: "",
        sta: "",
        orderName: "",
        nickName: "",
        boxId: "",
        orderCode: "",
        boxUnitPrice: "",
        leaseDuration: "",
        totalPrice: ""
      },
      userId: "",
      version: "",
      rules: {
        //校验
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
        this.editInfo.boxCode = val.boxCode;
        this.editInfo.sta = val.sta;
        this.editInfo.orderName = val.orderName;
        this.editInfo.nickName = val.nickName;
        this.editInfo.boxId = val.boxId;
        this.editInfo.orderCode = val.orderCode;
        this.editInfo.boxUnitPrice = val.boxUnitPrice;
        this.editInfo.leaseDuration = val.leaseDuration;
        this.editInfo.totalPrice = val.totalPrice;
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
          this.$axios.put("prod-api/backend/user/update", pamars).then(res => {
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