<template>
  <div class="all">
    <van-nav-bar title="我" fixed></van-nav-bar>
    <div class="upcard">
      <img class="img" src="../assets/head2.jpg" />
      <p class="name">{{ username }}</p>
      <p class="health" @click="tocommand">获取饮食推荐></p>
    </div>
    <van-divider
      :style="{ color: '#02e2c4', borderColor: '#02e2c4', padding: '0 16px' }"
    />
    <!-- -->
    <div>
      <van-grid>
        <van-grid-item icon="records" text="食物营养" @click="tofooddetail"/>
        <van-grid-item icon="exchange" text="修改资料" @click="tobase" />
        <van-grid-item icon="notes-o" text="健康报告" @click="toreport" />
        <van-grid-item icon="good-job-o" text="饮食推荐" @click="tocommand" />
      </van-grid>
      <van-grid>
        <van-grid-item icon="idcard" text="饮食记录" @click="toDiet" />
        <van-grid-item
          icon="smile-comment-o"
          text="饮水打卡"
          @click="toWater"
        />
        <van-grid-item icon="hot-o" text="查看文章" @click="toArticle" />
        <van-grid-item icon="replay" text="食物反馈" @click="ends" />
      </van-grid>
    </div>
    <van-dialog v-model="show" title="推荐饮食" show-cancel-button>
      <img src="../assets/fish.png" class="img2" />
      <p>香煎龙利鱼</p>
    </van-dialog>
    <van-tabbar v-model="active" fixed active-color="#02e2c4">
      <van-tabbar-item icon="home-o" @click="toHome">饮食</van-tabbar-item>
      <van-tabbar-item icon="points" @click="toWater">饮水</van-tabbar-item>
      <van-tabbar-item icon="thumb-circle-o" @click="toEat">食谱</van-tabbar-item>
      <van-tabbar-item icon="tv-o" @click="toArticle">文章</van-tabbar-item>
      <van-tabbar-item icon="user-o">我</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: 4,
      username: "",
      show: false,
    };
  },
  methods: {
    toHome() {
      this.$router.push("home");
    },
    tofooddetail(){
      this.$router.push("foodDetail");
    },
    toEat(){
      this.$router.push("eat");
    },
    toDiet() {
      this.$router.push("home");
    },
    toWater() {
      this.$router.push("water");
    },
    toArticle() {
      this.$router.push("article");
    },
    toDiet() {
      this.$router.push("home");
    },
    ends() {
      this.$router.push("advice");
    },
    tocommand() {
      this.$router.push("command");
      // this.show = true;
    },
    toArticle() {
      this.$router.push("article");
    },
    toWater() {
      this.$router.push("water");
    },
    getUsername() {
      this.username = localStorage.getItem("username");
    },
    tobase() {
      this.$router.push("updatebase");
    },
    toreport() {
      this.$router.push("report");
    },
  },
  mounted() {
    this.getUsername();
  },
};
</script>

<style scoped>
.all {
  margin: 0;
  padding: 0;
  height: 130vh;
  background: white;
  margin-top: -4%;
}
.upcard {
  width: 100%;
  height: 80px;
  /* text-align: center;
  margin: 0 auto; */
  position: relative;
  /* top: 2%; */
}
.img {
  height: 70px;
  width: 70px;
  border-radius: 50%;
  position: absolute;
  left: 3%;
  top: 13%;
}
.name {
  height: 70px;
  width: 70px;
  position: absolute;
  left: 23%;
  top: 8%;
  font-size: 16px;
  color: black;
}
.health {
  position: absolute;
  right: 5%;
  top: 28%;
  font-size: 15px;
  color: #02e2c4;
}
.img2 {
  height: 270px;
  width: 270px;
}
</style>