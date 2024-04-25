<template>
  <div class="all">
    <van-nav-bar title="食谱" fixed>
    </van-nav-bar>
    <div class="top">您的身材管理路线为：{{usertype}}</div>
    <div class="top2">为您推荐食谱如下</div>
    <div class="card" v-for="(item,i) in Eat1" :key="i" v-if="itemtype==1" @click="toDetail(item.id)">
      <img :src="item.pic" class="img">
      <p class="font">{{item.detail}}</p>
      <p class="font2">👉</p>
    </div>
    <div class="card" v-for="(item,i) in Eat2" :key="i" v-if="itemtype==2" @click="toDetail(item.id)">
      <img :src="item.pic" class="img">
      <p class="font">{{item.detail}}</p>
      <p class="font2">👉</p>
    </div>
    <div class="card" v-for="(item,i) in Eat3" :key="i" v-if="itemtype==3" @click="toDetail(item.id)">
      <img :src="item.pic" class="img">
      <p class="font">{{item.detail}}</p>
      <p class="font2">👉</p>
    </div>
    <van-tabbar v-model="active" fixed active-color="#02e2c4">
      <van-tabbar-item icon="home-o" @click="toHome">饮食</van-tabbar-item>
      <van-tabbar-item icon="points" @click="toWater">饮水</van-tabbar-item>
      <van-tabbar-item icon="thumb-circle-o">食谱</van-tabbar-item>
      <van-tabbar-item icon="tv-o" @click="toArticle">文章</van-tabbar-item>
      <van-tabbar-item icon="user-o" @click="toMe">我</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: 2,
      Eat:[],
      Eat1: [],
      Eat2: [],
      Eat3: [],
      show: false,
      UserBase:[],
      usertype:"",
      itemtype:"",
    };
  },
  methods: {
    toHome() {
      this.$router.push("home");
    },
    toDetail(i){
      console.log(i);
      localStorage.setItem("Eatid", i);
      this.$router.push("eatDetail");
    },
    getUserBase() {
      var id = localStorage.getItem("id");
      this.$axios.get("prod-api/user/getUserBase?userid=" + id).then((res) => {
        if (200 == res.status) {
          this.UserBase = res.data;
          if(this.UserBase.weight_type==1)this.itemtype=1;
          if(this.UserBase.weight_type==2)this.itemtype=2;
          if(this.UserBase.weight_type==3)this.itemtype=3;
          if(this.UserBase.weight_type==1)this.usertype="减肥";
          if(this.UserBase.weight_type==2)this.usertype="保持/塑性";
          if(this.UserBase.weight_type==3)this.usertype="增肥";
        } else {
          this.$toast.fail("请输入用户基本档案");
          this.$router.push("userBase");
        }
      });
    },
    toArticle() {
      this.$router.push("article");
    },
    toWater() {
      this.$router.push("water");
    },
    toMe() {
      this.$router.push("me");
    },
    showCalendar() {
      this.show = true;
    },
    getEat() {
      this.$axios.get("prod-api/user/getEat").then((res) => {
        if (200 == res.status) {
          this.Eat = res.data;
          for(var i =0 ;i<this.Eat.length;i++){
            if(this.Eat[i].hot==1)this.Eat1.push(this.Eat[i]);
            if(this.Eat[i].hot==2)this.Eat2.push(this.Eat[i]);
            if(this.Eat[i].hot==3)this.Eat3.push(this.Eat[i]);
          }
          console.log(this.Eat1);
        } else {
          console.log("null");
        }
      });
    },
  },
  mounted() {
    this.getEat();
    this.getUserBase();
  },
};
</script>

<style scoped>
.all {
  margin: 0;
  padding: 0;
  height: 170vh;
  background: white;
  margin-top: -4%;
}
.father {
  position: relative;
}
.pic {
  width: 110px;
  height: 150px;
}
.top{
  position: relative;
  top:10px;
  font-size: 18px;
  color: #02e2c4;
}
.top2{
  position: relative;
  top:10px;
  font-size: 18px;
  color: #02e2c4;
}
.card{
  position: relative;
  background:white;
  border-radius: 25px;
  margin:0 auto;
  text-align: center;
  width: calc(85% - 5px);
  height: 100px;
  margin-top:40px;
  box-shadow: 2px 2px 20px #9b9a9a;
}
.card .img{
  width:90px;
  height: 90px;
  position: absolute;
  top:4px;
  border-radius: 25px;
  left:15px;
}
.card .font{
  font-size: 18px;
  font-weight: 800;
  position: absolute;
  left:130px;
}
.font2{
  font-size: 25px;
  font-weight: 800;
  position: absolute;
  left:290px;
}
</style>