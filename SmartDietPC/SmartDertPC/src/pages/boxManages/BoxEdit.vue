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
       <el-form-item label="食谱名称" prop="detail" label-width="110px">
          <el-input v-model="editInfo.detail" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="早餐" prop="breakfast" label-width="110px">
          <el-input v-model="editInfo.breakfast" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="午餐" prop="lunch" label-width="110px">
          <el-input v-model="editInfo.lunch" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="晚餐" prop="dinner" label-width="110px">
          <el-input v-model="editInfo.dinner" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="适用人群" label-width="110px" prop="type">
          <el-select v-model="editInfo.hot" placeholder="请选择适用人群" clearable maxlength="40">
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
      dialogImageUrl:"",
      dialogVisible: false,
      editInfo: {
      id:"",
      detail:"",
      breakfast:"",
      lunch:"",
      dinner:"",
      pic:"",
      hot:"",
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
    success(res){
      this.editInfo.pic=res.thumbnail_url;
      console.log(this.editInfo.pic);
    },
    //打开dialog
    openDialog(flag, val) {
      this.dialogEdit = flag;
      this.$nextTick(() => {
        this.$refs.editInfo.resetFields();
        console.log(val.role);
        this.editInfo.detail = val.detail;
        this.editInfo.breakfast = val.breakfast;
        this.editInfo.lunch = val.lunch;
        this.editInfo.dinner = val.dinner;
        this.editInfo.hot = val.hot;
        this.editInfo.pic = val.pic;
        this.editInfo.id  = val.id;
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
          type: "error"
        });
        setTimeout(() => this.$refs.uploads.clearFiles(), 100);
        this.dialogVisible = false;
      } else if (!isLt2M) {
        this.$message({
          message: "上传文件不能超过10MB!",
          type: "error"
        });
      } else {
        let fd = new FormData();
        fd.append("file", file);
        this.$axios({
          method: "post",
          url: this.uploadUrl,
          data: fd
        }).then(res => {
          if (res.data.code == 200) {
            this.dialogImageUrl = res.data.fileName;
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: "success"
            });
          } else {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: "error"
            });
          }
        });
      }
    },
    //关闭dialog
    cancelDialog() {
      this.dialogEdit = false;
    },
    //修改
    editSure() {
      //this.dialogAdd = false;
      this.$refs["editInfo"].validate(valid => {
        if (valid) {
          //this.loading.startLoading();
          this.$axios
            .post("prod-api/user/editEat?userid=2"+"&detail="+this.editInfo.detail+"&breakfast="+this.editInfo.breakfast+"&lunch="+this.editInfo.lunch+"&dinner="+this.editInfo.dinner+"&hot="+this.editInfo.hot+"&pic="+this.editInfo.pic+"&id="+this.editInfo.id)
            .then(res => {
              //this.loading.endLoading();
              if (200 == res.status) {
                this.$message({
                  showClose: true,
                  message: "修改成功",
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