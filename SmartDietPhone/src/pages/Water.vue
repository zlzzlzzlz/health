<template>
  <div class="all">
    <van-nav-bar title="饮水" fixed> </van-nav-bar>
    <div class="upcard">
      <p
        style="
          position: absolute;
          left: 2%;
          top: -1%;
          font-size: 17px;
          color: black;
        "
      >
        每天建议饮水2000ml
      </p>
      <img class="img" src="../assets/water1.png" @click="addwater" v-if="show==1"/>
      <img class="img2" src="../assets/finish.png" @click="addwater" v-if="show==0"/>
      <p
        style="
          position: absolute;
          left: 33%;
          top: 55%;
          font-size: 17px;
          color: grey;
        "
        v-if="show==1"
      >
        点击饮水250ml
      </p>
      <p
        style="
          position: absolute;
          left: 4%;
          top: 68%;
          font-size: 17px;
          color:black;
        "
      >
        已饮水{{water3}}ml,完成{{water4}}%
      </p>
      <van-button round type="info" size="small"
      style="
          position: absolute;
          left: 75%;
          top: 72%;
          font-size: 15px;
        " @click="toDetail()">查看详情</van-button>
      <div v-for="(item,i) in water2" :key="i" class="under">
        <img src="../assets/class2.png" class="bottom" v-if="item==1"/>
        <img src="../assets/class.png" class="bottom" v-if="item==0"/>
      </div>
    </div>
    <div >

    </div>
    <!-- <div>
      <div v-for="(item,i) in water" :key="i" class="undercard"> 
        <p class="font">{{item.date}}</p>
      </div>
    </div> -->
    <van-tabbar v-model="active" fixed active-color="#02e2c4">
      <van-tabbar-item icon="home-o" @click="toHome">饮食</van-tabbar-item>
      <van-tabbar-item icon="points">饮水</van-tabbar-item>
      <van-tabbar-item icon="thumb-circle-o" @click="toEat">食谱</van-tabbar-item>
      <van-tabbar-item icon="tv-o" @click="toArticle">文章</van-tabbar-item>
      <van-tabbar-item icon="user-o" @click="toMe">我</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: 1,
      nowwater: 0,
      finish: 0,
      water: [],
      TodayWater:{},
      classnum:0,
      classnum2:0,
      water2:[],
      Todaydate:"",
      show:1,
      water3:0,
      water4:0,
      count:0,
    };
  },
  methods: {
    toHome() {
      this.$router.push("home");
    },
    toArticle(){
      this.$router.push("article");
    },
    toEat(){
      this.$router.push("eat");
    },
    toMe() {
      this.$router.push("me");
    },
    addwater() {
      if(this.classnum2<=8){
      var id = localStorage.getItem("id");
        this.$axios
          .post(
            "prod-api/user/updateWater?" +
              "userid=" +
              id +
              "&water=" +
              this.classnum2 +
              "&date=" + this.Todaydate +
              "&ml="+ this.classnum2*250
          )
          .then(res => {
            if (200 == res.status) {
              this.getwater();
            } else {
              this.$toast.fail("失败");
            }
          });
      }
      else{
        this.show=0;
      }
    },
    getwater() {
      var id = localStorage.getItem("id");
      this.$axios.get("prod-api/user/getWater?userid=" + id).then((res) => {
        if (200 == res.status) {
          this.water = res.data;
          console.log(this.water);
          //获取当前日期
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
          this.count=0;
          for (var i = 0; i < this.water.length; i++) {
            if (this.water[i].date == dateStr) {
              this.water2=[],
              this.TodayWater=this.water[i];
              this.classnum=this.water[i].water;
              this.classnum2=parseInt(this.classnum)+1;
              this.water3=this.TodayWater.ml;
              this.water4=parseInt(this.water3/20);
              if(this.classnum>=8){
                this.show=0;
              }
              for(var j=0;j<this.classnum;j++){
                this.water2.push(1);
              }
              for(var l=this.classnum;l<8;l++){
                this.water2.push(0);
              }
              console.log(this.water2);
              break;
            }
            this.count++;
          }
          if(this.count==this.water.length){
            var id = localStorage.getItem("id");
            this.$axios
            .post(
            "prod-api/user/addWater?" +
              "userid=" +
              id +
              "&water=" +
              0 +
              "&date=" + this.Todaydate +
              "&ml="+ 0
          )
          .then(res => {
            if(200 == res.status){
              console.log("success");
              this.getwater();
            }
            else{
              console.log("fail");
            }
          });
          }
        } else {
          console.log("null");
        }
      });
    },
    toDetail(){
    this.$router.push("waterDetail");
    },
  },
  mounted() {
    this.getwater();
  },
};
</script>

<style scoped>
.all {
  margin: 0;
  padding: 0;
  height: 100vh;
  background: white;
  margin-top: -4%;
  background-image:url("../assets/background.png");
}
.upcard {
  top: 5%;
  position: relative;
  background: white;
  margin: 0 auto;
  width: calc(100% - 50px);
  height: 350px;
  display: flex;
  box-shadow: 2px 2px 20px #9b9a9a;
}
.img {
  position: absolute;
  height: 130px;
  width: 180px;
  top: 20%;
  left: 25%;
}
.img2 {
  position: absolute;
  height: 210px;
  width: 280px;
  top: 20%;
  left: 10%;
}
.bottom {
  height: 45px;
  width: 45px;
}
.under {
  height: 130px;
  width: 180px;
  margin-top: 78%;
}
.undercard{
  top: 70px;
  position: relative;
  background: white;
  margin: 0 auto;
  margin-top: 2px;
  width: calc(100% - 50px);
  height: 150px;
  display: flex;
  box-shadow: 2px 2px 20px #9b9a9a;
}
/* .font{
  font-size:19px;
  position: absolute;
  left: 8px;

} */
</style>