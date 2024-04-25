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
        <el-form-item label="食谱名称" prop="detail" label-width="110px">
          <el-input v-model="detail" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="早餐" prop="breakfast" label-width="110px">
          <el-input v-model="breakfast" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="午餐" prop="lunch" label-width="110px">
          <el-input v-model="lunch" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="晚餐" prop="dinner" label-width="110px">
          <el-input v-model="dinner" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="适用人群" label-width="110px" prop="type">
          <el-select v-model="hot" placeholder="请选择适用人群" clearable maxlength="40">
            <el-option label="减肥用户" value="1" maxlength="40"></el-option>
            <el-option label="塑性用户" value="2" maxlength="40"></el-option>
            <el-option label="增肌用户" value="3" maxlength="40"></el-option>
          </el-select>
        </el-form-item>
         <el-form-item label="食物图片" prop="imgUrl" label-width="110px">
          <el-upload 
            target="form"
            enctype="multipart/form-data" 
            method="post" 
            action="http://test.imgurl.org/api/upload?token=0kePwAJUQ8IGvjT3qbgQfDvhTgnCOXqIpUrEiiVn"
            list-type="picture-card"
            :on-success="success"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt />
          </el-dialog>
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
      detail:"",
      breakfast:"",
      lunch:"",
      dinner:"",
      pic:"",
      hot:"",
      dialogImageUrl: "",
      dialogVisible: false,
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
  mounted() {},
  methods: {
    //打开dialog
    openDialog(flag, val) {
      this.dialogAdd = flag;
      this.$nextTick(() => {
        this.$refs.addInfo.resetFields();
      });
    },
    success(res){
      this.pic=res.thumbnail_url;
      console.log(this.pic);
    },
    //关闭dialog
    cancelDialog() {
      this.dialogAdd = false;
    },
    getBinaryFromFile(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();

        reader.addEventListener("load", () => resolve(reader.result));
        reader.addEventListener("error", err => reject(err));

        reader.readAsBinaryString(file);
    });
},
    beforeAvatarUpload(file) {
      console.log(file);
      var testmsg = file.name.substring(file.name.lastIndexOf(".") + 1);
      const jpgPic = testmsg === "jpg";
      const pngPic = testmsg === "png";
      const isLt2M = file.size / 1024 / 1024 < 10;
      if (!jpgPic && !pngPic) {
        this.$message({
          message: "上传文件只能是jpg、png格式!",
          type: "error",
        });
        setTimeout(() => this.$refs.uploads.clearFiles(), 100);
        this.dialogVisible = false;
      } else if (!isLt2M) {
        this.$message({
          message: "上传文件不能超过10MB!",
          type: "error",
        });
      } else {
        let fd = new FormData();
        fd.append("file", file);
        console.log(file)
        // let bin=this.getBinaryFromFile(file);
        // console.log(bin)
        this.$axios({
          method: "post",
          url: "https://domain.com/api/upload",
          file: fd,
          headers: {'Content-Type':'multipart/form-data'},
          token:"0kePwAJUQ8IGvjT3qbgQfDvhTgnCOXqIpUrEiiVn"
        })
          .then((res) => {
            if (res.data.code == 200) {
              this.dialogImageUrl = res.data.fileName;
              console.log(this.dialogImageUrl);
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: "success",
              });
            } else {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: "error",
              });
            }
          });
      }
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
            .post("prod-api/user/addEat?userid=2"+"&detail="+this.detail+"&breakfast="+this.breakfast+"&lunch="+this.lunch+"&dinner="+this.dinner+"&hot="+this.hot+"&pic="+this.pic)
            .then(res => {
              //this.loading.endLoading();
              if (200 == res.status) {
                this.$message({
                  showClose: true,
                  message: "添加成功",
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
