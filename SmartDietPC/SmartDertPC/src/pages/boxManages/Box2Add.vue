<template>
  <div class="boxDialog">
    <el-dialog width="400px" title :visible.sync="dialogAdd" :close-on-click-modal="true">
      <el-form
        :model="addInfo"
        label-width="100px"
        :rules="rules"
        ref="addInfo"
        :inline="true"
        label-position="right"
      >
        <el-form-item label="箱子规格" prop="boxStandard" label-width="110px">
          <el-select v-model="addInfo.boxStandard" placeholder="请选择" @change="StandardChange">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.boxStandard"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所需积分" prop="boxUnitPrice" label-width="110px">
          <el-input v-model="addInfo.boxUnitPrice" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark" label-width="110px">
          <el-input v-model="addInfo.remark" maxlength="40" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="addSure('addInfo')">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "addData",
  data() {
    function teshu(str) {
      var reg = /[`~!@#$%^&()_\-+=<>?:"{}|,.\/;'\\[\]·~！@#￥%……&（）——\-+={}|《》？：“”【】、；‘'，。、]/im; /*定义验证表达式*/
      return reg.test(str); /*进行验证*/
    }
    //密码校验
    var validatePassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入箱子规格"));
      }
      if (teshu(value) == true) {
        callback(new Error("请勿输入特殊字符"));
      } else {
        callback();
      }
    };
    return {
      options: [],
      dialogAdd: false,
      addInfo: {
        boxStandard: "",
        boxUnitPrice: "",
        remark: "",
        inventoryNumber: "",
        memberPoints: "",
        note: ""
      },
      rules: {
        //校验
        boxCode: [
          {
            required: true,
            validator: validatePassword,
            trigger: "blur"
          }
        ],

        boxId: [
          {
            required: true,
            pattern: /^[0-9]*$/,
            trigger: "blur",
            message: "请输入数字"
          }
        ],
        state: [
          {
            required: true,
            pattern: /^[1]*$/,
            trigger: "blur",
            message: "请输入数字"
          }
        ],
        memberPoints: [
          {
            required: true,
            pattern: /^[0-9]*$/,
            trigger: "blur",
            message: "请输入数字"
          }
        ],
        boxUser: [
          {
            required: true,
            pattern: /^[\u4E00-\u9FA5A-Za-z]+$/,
            trigger: "blur",
            message: "请输入1-20位中文或英文"
          }
        ],
        note: [
          {
            required: true,
            pattern: /^[\u4E00-\u9FA5A-Za-z]+$/,
            trigger: "blur",
            message: "请输入1-20位中文或英文"
          }
        ]
      }
    };
  },
  mounted() {
    this.standradDown();
  },
  methods: {
    //选择箱子change事件
    StandardChange(value) {
      this.addInfo.boxStandard = value.boxStandard;
      this.addInfo.boxUnitPrice = value.boxUnitPrice;
    },
    //箱子规格下拉列表
    standradDown() {
      this.$axios.get("prod-api/backend/box/standard/select").then(res => {
        if (res.data.code == 200) {
          this.options = res.data.data;
        } else {
          this.$message({
            showClose: true,
            message: res.data.msg,
            type: "error"
          });
        }
      });
    },
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
      this.$refs["addInfo"].validate(valid => {
        if (valid) {
          //this.loading.startLoading();
          let pamars = {
            boxStandard: this.addInfo.boxStandard,
            boxUnitPrice: this.addInfo.boxUnitPrice,
            inventoryNumber: this.addInfo.inventoryNumber,
            remark: this.addInfo.remark
          };
          this.$axios
            .post("prod-api/backend/box/info/create", pamars)
            .then(res => {
              //this.loading.endLoading();
              if (200 == res.data.code) {
                this.$message({
                  showClose: true,
                  message: res.data.msg,
                  type: "success"
                });
                this.$emit("search");
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
    }
  }
};
</script>

<style scoped>
</style>