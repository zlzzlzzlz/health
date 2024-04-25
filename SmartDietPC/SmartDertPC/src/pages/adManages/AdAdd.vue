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
          <!-- <form enctype="multipart/form-data" method="post" action="http://test.imgurl.org/api/upload" id="upform">
    <input type="file" name="file" class="up-file">
    <input type="submit" id="btn" value="开始上传" class="btn">
    </form> -->
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt />
          </el-dialog>
        </el-form-item>
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
      food_pic:"",
      food_heat:"",
      food_name:"",
      dialogAdd: false,
      dialogImageUrl: "",
      dialogVisible: false,
      addInfo: {
        title: "",
        content: "",
        points: "",
        imgUrl: "",
        sortNo: "",
      },
      uploadUrl: "https://domain.com/api/upload?token=0kePwAJUQ8IGvjT3qbgQfDvhTgnCOXqIpUrEiiVn",
      rules: {
        //校验
        title: [
          {
            required: true,
            trigger: "blur",
            message: "请输入正确的标题",
          },
        ],
        content: [
          {
            required: true,
          },
        ],
        points: [
          {
            required: true,
          },
        ],
        imgUrl: [{}],
        sortNo: [
          {
            required: true,
          },
        ],
      },
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
      this.dialogAdd = flag;
      this.$nextTick(() => {
        this.$refs.addInfo.resetFields();
      });
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
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
    getBinaryFromFile(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();

        reader.addEventListener("load", () => resolve(reader.result));
        reader.addEventListener("error", err => reject(err));

        reader.readAsBinaryString(file);
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
            food_name: this.food_name,
            food_heat: this.food_heat,
            food_pic: this.food_pic,
          };
          this.$axios
            .post("prod-api/food/addFood?food_name="+this.food_name+"&food_heat="+this.food_heat+"&food_pic="+this.food_pic)
            .then((res) => {
              //this.loading.endLoading();
              if (200 == res.status) {
                this.$message({
                  showClose: true,
                  message: "添加成功",
                  type: "success",
                });
                this.$emit("search");
                this.cancelDialog();
              } else {
                this.$message({
                  showClose: true,
                  message: "添加失败",
                  type: "error",
                });
              }
            });
        }
      });
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    // handlePictureCardPreview(file) {
    //   this.dialogImageUrl = file.url;
    //   this.dialogVisible = true;
    // }
  },
};
</script>

<style scoped>
</style>