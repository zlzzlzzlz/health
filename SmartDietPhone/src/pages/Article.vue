<template>
  <div class="all">
    <van-nav-bar title="文章" fixed></van-nav-bar>
    <div v-for="(item, i) in article" class="top" >
      <p class="p1" @click="toDetail(i)">{{ item.title }}</p>
      <p @click="toDetail(i)">{{ item.article }}</p>
      <van-icon name="fire-o"  class="hot" size="30px" :info="item.hot" @click="addHot(item.title,item.hot,i)" v-if="Hot[i]==0"/>
      <van-icon name="fire-o"  class="hot" size="30px" :info="item.hot" color="#ee0a24"  v-if="Hot[i]==1"/>
    </div>
    <div>
      <img class="img2" src="../assets/write.png" @click="toUp" />
    </div>
    <van-tabbar v-model="active" fixed active-color="#02e2c4">
      <van-tabbar-item icon="home-o" @click="toHome">饮食</van-tabbar-item>
      <van-tabbar-item icon="points" @click="toWater">饮水</van-tabbar-item>
      <van-tabbar-item icon="thumb-circle-o" @click="toEat">食谱</van-tabbar-item>
      <van-tabbar-item icon="tv-o">文章</van-tabbar-item>
      <van-tabbar-item icon="user-o" @click="toMe">我</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: 3,
      username: "",
      article: [],
      Hot:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
    };
  },
  methods: {
    toHome() {
      this.$router.push("home");
    },
    toUp() {
      this.$router.push("uploadArticle");
    },
    toWater() {
      this.$router.push("water");
    },
    toEat(){
      this.$router.push("eat");
    },
    toMe() {
      this.$router.push("me");
    },
    addHot(title,hot,i){
      this.$axios
          .post(
            "prod-api/user/addHot?" +
              "title=" +
              title +
              "&hot=" +
              (parseInt(hot)+1)
          )
          .then(res => {
            if (200 == res.status) {
              this.getarticle();
              this.Hot[i]=1;
              console.log(this.Hot);
              localStorage.setItem("hot",JSON.stringify(this.Hot));
            } else {
              this.$toast.fail("失败");
            }
          });
    },
    getHot(){
        this.Hot=JSON.parse(localStorage.getItem("hot"));
    },
    getarticle() {
      this.$axios.get("prod-api/user/getArticle").then((res) => {
        if (200 == res.status) {
          this.article = res.data;
          for (var i = 0; i < this.article.length; i++) {
            this.article[i].article = this.article[i].article.slice(0, 70);
          }
          console.log(this.article);
        } else {
          console.log("null");
        }
      });
    },
    toDetail(i) {
      localStorage.setItem("i", i);
      this.$router.push("articleDetail");
    },
  },
  mounted() {
    this.getarticle();
    // this.getHot();
  },
};
</script>

<style scoped>
.all {
  margin: 0;
  padding: 0;
  background: white;
  margin-top: -4%;
  height: 180vh;
}
.top {
  float: left;
  margin-top: 3px;
  background: url("../assets/read.png");
  width: 100%;
  font-weight: 600;
}
.top .p1 {
  font-size: 19px;
  font-weight: 700;
}
.fire {
  position: relative;
}
.p4 {
  position: absolute;
}
.p3 {
  position: absolute;
}
.img2 {
  width: 80px;
  height: 80px;
  left: 50%;
  top: 50%;
  margin-left: -188px;
  margin-top: 180px;
  position: fixed;
  z-index: 1;
}
.hot{
  left: 160px;
}
</style>