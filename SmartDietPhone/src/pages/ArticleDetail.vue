<template>
  <div class="alldiv">
    <div>
      <van-nav-bar
        title="文章详情"
        left-arrow
        fixed
        @click-left="onClickLeft"
      />
    </div>
    <div class="article">
        <p class="title">{{articleDetail.title}}</p>
        <div class="article2">{{articleDetail.article}}</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      article: [],
      articleDetail: [],
    };
  },
  methods: {
    onClickLeft() {
      this.$router.push("article");
    },
    getarticle() {
      this.$axios.get("prod-api/user/getArticle").then((res) => {
        if (200 == res.status) {
          this.article = res.data;
          var i = localStorage.getItem("i");
          console.log(i);
          this.articleDetail=this.article[i]
          console.log( this.articleDetail);
        } else {
          console.log("null");
        }
      });
    },
  },
  mounted() {
    this.getarticle();
  },
};
</script>


<style scoped>
.alldiv {
  margin: 0;
  padding: 0;
  height: 100vh;
  background: url("../assets/read.png");
  filter:alpha(opacity=50);
  margin-top: -4%;
}
.article{
    position: relative;
}
.title{
    /* position: absolute; */
    margin:0 auto;
    font-size: 25px;
}
.article2{
    position: absolute;
    top:50px;
    font-size: 19px;
}
</style>