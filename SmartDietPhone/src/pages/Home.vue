<!-- 想法：建一个mydiet的表，字段有早餐、午餐、晚餐、日期、用户id，根据用户id和日期查询  -->
<template>
  <div class="all">
    <van-nav-bar title="饮食" fixed>
      <template #right>
        <van-icon name="notes-o" size="18" @click="showCalendar" />
      </template>
    </van-nav-bar>
    <van-calendar
      v-model="show"
      @confirm="onConfirm"
      color="#02e2c4"
      :min-date="minDate"
    />
    <div class="upcard">
      <p
        style="
          position: absolute;
          right: 1%;
          top: -5%;
          font-size: 14px;
          color: #747474;
        "
      >
        单位：千卡
      </p>
      <div class="incard1">
        <span>已摄入</span>
        <span class="heat1">{{ allheat }}</span>
      </div>
      <van-circle
        v-model="currentRate"
        :rate="100"
        :text="text"
        :color="gradientColor"
        layer-color="#ebedf0"
        class="incard2"
        v-if="heatflag==0"
      />
      <span class="heat" v-if="this.heatflag==0">{{ heat }}</span>
       <van-circle
        v-model="currentRate"
        :rate="100"
        :text="text2"
        color="red"
        layer-color="#ebedf0"
        class="incard2"
        v-if="heatflag==1"
      />
      <span class="heat" v-if="this.heatflag==1">{{ heat3 }}</span>
      <div class="incard3">
        <span>推荐摄入</span>
        <span class="heat1">{{ heat2 }}</span>
      </div>
    </div>
    <div style="margin-top:60px;color:green;font-size:18px" @click="tofooddetail">吃前先查查</div>
    <div class="diet">
      <van-collapse v-model="activeNames">
        <van-collapse-item value="早餐" name="1" size="large">
          <!-- <p>{{breakfastheat}}</p> -->
          <p v-if="BreakfastUserDiet == null">还没有添加食物</p>
          <div
            v-for="(food, i) in BreakfastUserDiet"
            class="foodcard"
            v-if="food.date == date2"
          >
            <div>
              <img :src="food.food_pic" class="foodimg" />
              <p class="foodname">{{ food.breakfast }}</p>
              <p class="foodheat">{{ food.weight }}克</p>
              <div class="foodicon">{{ food.allheat }}千卡</div>
            </div>
          </div>
          <a style="color: #02e2c4" @click="toSecletFood">去添加</a>
        </van-collapse-item>
        <van-collapse-item value="午餐" name="2" size="large">
          <p v-if="LunchUserDiet == null">还没有添加食物</p>
          <div
            v-for="(food, i) in LunchUserDiet"
            class="foodcard"
            v-if="food.date == date2"
          >
            <img :src="food.food_pic" class="foodimg" />
            <p class="foodname">{{ food.lunch }}</p>
            <p class="foodheat">{{ food.weight }}克</p>
            <div class="foodicon">{{ food.allheat }}千卡</div>
          </div>
          <a style="color: #02e2c4" @click="toSecletFood">去添加</a>
        </van-collapse-item>
        <van-collapse-item value="晚餐" name="3" size="large">
          <p v-if="DinnerUserDiet == null">还没有添加食物</p>
          <div
            v-for="(food, i) in DinnerUserDiet"
            class="foodcard"
            v-if="food.date == date2"
          >
            <img :src="food.food_pic" class="foodimg" />
            <p class="foodname">{{ food.dinner }}</p>
            <p class="foodheat">{{ food.weight }}克</p>
            <div class="foodicon">{{ food.allheat }}千卡</div>
          </div>
          <a style="color: #02e2c4" @click="toSecletFood">去添加</a>
        </van-collapse-item>
      </van-collapse>
    </div>

    <van-tabbar v-model="active" fixed active-color="#02e2c4">
      <van-tabbar-item icon="home-o">饮食</van-tabbar-item>
      <van-tabbar-item icon="points" @click="toWater">饮水</van-tabbar-item>
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
      activeNames: ["1"],
      UserBase: {},
      active: 0,
      currentRate: 0,
      gradientColor: {
        "0%": "#3fecff",
        "100%": "#02e2c4",
      },
      heat1: 200,
      heat: 0,
      heat2: 2400,
      UserDiet: {},
      BreakfastUserDiet: [],
      LunchUserDiet: [],
      DinnerUserDiet: [],
      date2: "",
      show: false,
      minDate: new Date(2021, 0, 1),
      allheat: 0,
      heatflag:0,
      heat3:0,
      bmi: 0,
      height:0,
      weight:0,
      // breakfastheat: 0,
      // lunchheat: 0,
      // dinnerheat: 0
      // list: [],
      // loading: false,
      // finished: false
    };
  },
  methods: {
    toWater() {
      this.$router.push("water");
    },
    toEat(){
      this.$router.push("eat");
    },
    toArticle(){
      this.$router.push("article");
    },
    tofooddetail(){
      this.$router.push("foodDetail");
    },
    getDate() {
      var date2 = new Date();
      var seperator12 = "-";
      var year2 = date2.getFullYear();
      var month2 = date2.getMonth() + 1;
      var strDate2 = date2.getDate();
      if (month2 >= 1 && month2 <= 9) {
        month2 = "0" + month2;
      }
      if (strDate2 >= 0 && strDate2 <= 9) {
        strDate2 = "0" + strDate2;
      }
      var currentdate2 = year2 + seperator12 + month2 + seperator12 + strDate2;
      this.date2 = currentdate2;
    },
    onLoad() {
      // 异步更新数据
      // setTimeout 仅做示例，真实场景中一般为 ajax 请求
      setTimeout(() => {
        for (let i = 0; i < 10; i++) {
          this.list.push(this.list.length + 1);
        }

        // 加载状态结束
        this.loading = false;

        // 数据全部加载完成
        if (this.list.length >= 40) {
          this.finished = true;
        }
      }, 1000);
    },
    toSecletFood() {
      this.$router.push("selectfood");
    },
    formatDate(date) {
      var seperator13 = "-";
      var year3 = date.getFullYear();
      var month3 = date.getMonth() + 1;
      var strDate3 = date.getDate();
      if (month3 >= 1 && month3 <= 9) {
        month3 = "0" + month3;
      }
      if (strDate3 >= 0 && strDate3 <= 9) {
        strDate3 = "0" + strDate3;
      }
      var currentdate3 = year3 + seperator13 + month3 + seperator13 + strDate3;
      return currentdate3;
    },
    onConfirm(date) {
      this.show = false;
      this.date2 = this.formatDate(date);
      this.getallheat();
    },
    showCalendar() {
      this.show = true;
    },
    getUserDiet() {
      var id = localStorage.getItem("id");
      this.$axios.get("prod-api/user/getUserDiet?userid=" + id).then((res) => {
        if (200 == res.status) {
          this.UserDiet = res.data;
          for (var i = 0; i < this.UserDiet.length; i++) {
            if (this.UserDiet[i].breakfast)
              this.BreakfastUserDiet.push(this.UserDiet[i]);
            if (this.UserDiet[i].lunch)
              this.LunchUserDiet.push(this.UserDiet[i]);
            if (this.UserDiet[i].dinner)
              this.DinnerUserDiet.push(this.UserDiet[i]);
          }
          this.getallheat();
        } else {
          console.log("null");
        }
      });
    },
    getUserBase() {
      var id = localStorage.getItem("id");
      this.$axios.get("prod-api/user/getUserBase?userid=" + id).then((res) => {
        if (200 == res.status) {
          this.UserBase = res.data;
          this.height=res.data.height/100;
          this.weight=res.data.weight;
          var temp=parseFloat(this.height)*parseFloat(this.height)
        //   this.bmi=(this.weight.toFixed(2)/((this.height.toFixed(2))*(this.height.toFixed(2))));
          this.bmi=(parseFloat(this.weight)/temp).toFixed(2);
          console.log(this.bmi);
          if(this.UserBase.weight_type==1){
            if(this.bmi<=22)this.heat2=2100;
            else this.heat2=1950;
          }
          if(this.UserBase.weight_type==2){
            if(this.bmi<=22)this.heat2=2450;
            else this.heat2=2300;
          }
          if(this.UserBase.weight_type==3){
            if(this.bmi<=22)this.heat2=2650;
            else this.heat2=2600;
          }
        } else {
          this.$toast.fail("请输入用户基本档案");
          this.$router.push("userBase");
        }
      });
    },
    getallheat() {
      this.allheat = 0;
      for (var i = 0; i < this.UserDiet.length; i++) {
        if (this.UserDiet[i].date == this.date2)
          this.allheat += parseInt(this.UserDiet[i].allheat);
      }
      this.heat = this.heat2 - this.allheat;
      this.currentRate = (this.allheat / this.heat2) * 100;
      if(this.allheat>=this.heat2){
        this.$toast.fail("饮食热量超出推荐摄入热量，请合理安排膳食！");
        this.heatflag=1;
        this.heat3=Math.abs(this.heat);
      }
    },
    toMe() {
      this.$router.push("me");
    },
    // getBreakfastheat() {
    //   for (var i in this.BreakfastUserDiet) {
    //     console.log("111");
    //     if (this.BreakfastUserDiet[i].date == this.date2) {
    //       this.breakfastheat += this.BreakfastUserDiet[i].allheat;
    //     }
    //   }
    // }   不好用
  },
  mounted() {
    this.getUserBase();
    this.getUserDiet();
    // this.getBreakfastheat();
  },
  computed: {
    text() {
      return "还可以吃";
      // return this.currentRate.toFixed(0) + "%";
    },
    text2() {
      return "已超标";
      // return this.currentRate.toFixed(0) + "%";
    },
  },
  created() {
    this.getDate();
  },
};
</script>
<style >
.van-nav-bar .van-icon {
  color: #02e2c4;
}
</style>
<style scoped>
.all {
  margin: 0;
  padding: 0;
  height: 170vh;
  background: white;
  margin-top: -4%;
}
.foodicon {
  position: absolute;
  color: black;
  font-size: 13px;
  right: 20px;
  top: calc(50% - 25px);
}
.foodheat {
  position: absolute;
  top: 20px;
  left: -10px;
  width: 200px;
  color: grey;
  font-size: 13px;
}
.foodname {
  position: absolute;
  top: -10px;
  left: 35px;
  width: 100px;
  font-weight: 500;
  color: black;
}
.foodcard {
  margin-top: 2px;
  width: 100%;
  height: 70px;
  background: white;
  position: relative;
}
.foodimg {
  position: absolute;
  left: 1px;
  width: 50px;
  height: 50px;
  top: 5px;
}
.diet {
  position: relative;
  top: 1%;
}
.upcard {
  top: 3%;
  position: relative;
  background: white;
  margin: 0 auto;
  width: calc(100% - 50px);
  height: 200px;
  display: flex;
  box-shadow: 2px 2px 20px #9b9a9a;
}
.incard1 {
  position: relative;
  top: 35%;
  left: 18%;
  font-size: 10px;
  color: #747474;
}
.incard2 {
  align-self: center;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
}
.incard3 {
  position: relative;
  top: 35%;
  left: -15%;
  font-size: 10px;
  color: #747474;
}
.heat1 {
  position: absolute;
  top: 13%;
  left: 5%;
  color: #030303;
  font-size: 20px;
}
.heat {
  position: absolute;
  top: 48%;
  left: 42.5%;
  color: #030303;
  font-size: 20px;
}
/* .down {
  position: absolute;
  top: 88%;
  left: 38.5%;
  color: #02e2c4;
  font-size: 12px;
} */
</style>
<style >
.van-circle__text {
  top: 35%;
  font-size: 10px;
  color: #747474;
}
</style>