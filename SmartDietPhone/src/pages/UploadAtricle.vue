<template>
  <div class="alldiv">
    <div>
      <van-nav-bar
        title="发布文章"
        left-arrow
        fixed
        @click-left="onClickLeft"
      />
    </div>
    <!-- <van-uploader v-model="fileList" :after-read="afterRead" multiple>
      <template #preview-cover="{ file }">
        <div class="preview-cover van-ellipsis">{{ file.name }}</div>
      </template>
    </van-uploader> -->
    <van-cell-group class="up">
      <van-field v-model="title" label="标题" placeholder="请输入标题" autosize/>
    </van-cell-group>
    <van-field
  v-model="message"
  rows="20"
  autosize
  label="文章详细"
  type="textarea"
  maxlength="800"
  placeholder="请输入文章详细"
  show-word-limit
/>
<van-button type="info" @click="submit">提  交</van-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fileList: [],
      title: "",
      message:"",
      Todaydate:"",
      hot:"1",
    };
  },
  methods: {
    // afterRead(file) {
    //   // 此时可以自行将文件上传至服务器
    //   var formData = new FormData()
    //                 formData.append('file1', file)
    //                 this.$axios.post(formData).then(res => {
    //                     console.log(res)
    //                 }).catch(err => {
    //                     console.log(err)
    //                 })
    //   console.log(this.fileList);
    // },
    // 俗话说减肥七分吃三分动，这句话不是没有道理的，运动只是辅助减肥，但是饮食习惯可能直接导致体质差，
    // 最终可能减下来但是因为不好的饮食习惯而反弹！减肥期间可以吃一些清淡的炒菜，像水煮肉片这种重油重盐的就算了吧，很多人不会做饭，
    // 对油的存在没有感觉，油可能藏在我们平时不知道的角落：比如蛋糕里就有很多油，油炸食品看起来干干的但是油真的很多，
    // 很多川菜里油也非常多，蛋黄酱好吃，但是它基本上全部是脂肪，酸奶油酱也是很多脂肪。
    submit(){
      var id = localStorage.getItem("id");
       var nowDate = new Date();
          var year = nowDate.getFullYear();
          var month =
            nowDate.getMonth() + 1 < 10
              ? "0" + (nowDate.getMonth() + 1)
              : nowDate.getMonth() + 1;
          var day =
            nowDate.getDate() < 10
              ? "0" + nowDate.getDate()
              : nowDate.getDate();
          var dateStr = year + "-" + month + "-" + day;
          this.Todaydate=dateStr;
       this.$axios
          .post(
            "prod-api/user/addArticle?" +
              "userid=" +
              id +
              "&title=" +
              this.title +
              "&article=" + this.message +
              "&date="+ this.Todaydate +"&hot="+ this.hot
          )
          .then(res => {
            if (200 == res.status) {
              // this.$toast.success("记得喝水哦~");
              this.$toast.success("提交成功");
            } else {
              this.$toast.fail("失败");
            }
          });
    },
    onClickLeft() {
      this.$router.push("article");
    },
  },
};
</script>

<style scoped>
.preview-cover {
  position: absolute;
  bottom: 0;
  box-sizing: border-box;
  width: 100%;
  padding: 4px;
  color: #fff;
  font-size: 12px;
  text-align: center;
  background: rgba(0, 0, 0, 0.3);
}
.alldiv {
  margin: 0;
  padding: 0;
  height: 100vh;
  background: white;
  margin-top: -4%;
}
.up{
  position: relative;
  margin-top:5%;
}
</style>