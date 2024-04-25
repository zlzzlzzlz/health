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
         <el-form-item label="食物名称" prop="food_name" label-width="110px">
          <el-input v-model="food_name" maxlength="40" clearable></el-input>
        </el-form-item>
        <el-form-item label="食物热量/100g" prop="food_heat" label-width="110px">
          <el-input
            v-model="food_heat"
            maxlength="40"
            clearable
          ></el-input>
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
      dialogImageUrl: "",
      dialogVisible: false,
      food_pic:"",
      food_heat:"",
      food_name:"",
      dialogAdd: false,
      editInfo: {
        id: "",
        title: "",
        content: "",
        points: "",
        imgUrl: "",
        sortNo: ""
      },
      version: "",
      uploadUrl: "prod-api/common/upload",
      imgUrl: "", //原图片地址
      rules: {
        //校验
        title: [
          {
            required: true
          }
        ],
        content: [
          {
            required: true
          }
        ],
        points: [
          {
            required: true
          }
        ],
        imgUrl: [{}],
        sortNo: [
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
      this.food_pic=res.thumbnail_url;
      console.log(this.food_pic);
    },
    //打开dialog
    openDialog(flag, val) {
      this.dialogEdit = flag;
      this.imgUrl = val.imgUrl;
      this.$nextTick(() => {
        this.$refs.editInfo.resetFields();
        this.food_name = val.food_name;
        this.food_heat = val.food_heat;
        this.food_pic = val.food_pic;
        this.food_id=val.food_id;
        this.editInfo.points = val.points;
        this.editInfo.sortNo = val.sortNo;
        this.version = val.version;
      });
    },
    //关闭dialog
    cancelDialog() {
      this.dialogEdit = false;
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
    //修改
    editSure() {
      this.$refs["editInfo"].validate(valid => {
        if (valid) {
          this.$axios
            .post("prod-api/food/updateFood?food_name="+this.food_name+"&food_heat="+this.food_heat+"&food_pic="+this.food_pic+"&food_id="+this.food_id)
            .then(res => {
              if (200 == res.status) {
                this.$message({
                  showClose: true,
                  message: "修改成功",
                  type: "success",
                });
                this.$emit("search");
                this.cancelDialog();
              }
            });
        }
      });
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    }
  }
};
</script>

<style scoped>
</style>