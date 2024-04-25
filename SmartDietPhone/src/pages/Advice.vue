<template>
  <div class="alldiv">
    <div>
      <van-nav-bar
        title="食物反馈"
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
      <van-field v-model="title" label="食物名称" placeholder="请输入要反馈的食物名称" autosize/>
    </van-cell-group>
    <van-field
  v-model="message"
  rows="20"
  autosize
  label="食物详细"
  type="textarea"
  maxlength="100"
  placeholder="请输入要反馈的食物详细"
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
      username:"",
    };
  },
  methods: {
    submit(){
       this.$axios
          .post(
            "prod-api/user/addAdvice?" +
              "&advice=" +
              this.title +
              "&detail=" + this.message +
              "&name="+ this.username
          )
          .then(res => {
            if (200 == res.status) {
              this.$toast.success("提交成功");
              this.$router.push("home");
            } else {
              this.$toast.fail("失败");
            }
          });
    },
    onClickLeft() {
      this.$router.push("home");
    },
    getUsername() {
      this.username = localStorage.getItem("username");
    },
  },
  mounted() {
    this.getUsername();
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