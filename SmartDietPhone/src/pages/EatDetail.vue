<template>
  <div class="all">
    <div>
      <van-nav-bar
        title="食谱详情"
        left-arrow
        fixed
        @click-left="onClickLeft"
      />
    </div>
    <img :src="pic" class="img">
    <p>早餐：{{breakfast}}</p>
    <p>午餐：{{lunch}}</p>
    <p>晚餐：{{dinner}}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      breakfast: "",
      lunch: "",
      dinner: "",
      pic: "",
    };
  },
  methods: {
    onClickLeft() {
      this.$router.push("eat");
    },
    gettheEat() {
      var id = localStorage.getItem("Eatid");
      this.$axios.get("prod-api/user/gettheEat?id=" + id).then((res) => {
        if (200 == res.status) {
          console.log(res.data);
          this.pic = res.data.pic;
          this.breakfast = res.data.breakfast;
          this.lunch = res.data.lunch;
          this.dinner = res.data.dinner;
        } else {
          console.log("null");
        }
      });
    },
  },
  mounted() {
    this.gettheEat();
  },
};
</script>

<style scoped>
.card{
  position: relative;
  background:white;
  border-radius: 25px;
  margin:0 auto;
  text-align: center;
  width: calc(95% - 5px);
  height: 250px;
  margin-top:40px;
  box-shadow: 2px 2px 20px #9b9a9a;
}
.all {
  margin: 0;
  padding: 0;
  height: 170vh;
  background: white;
  margin-top: -4%;
}
.img{
    width: 100%;
    height: 300px;
    opacity: 0.9;
}
</style>