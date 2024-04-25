<template>
  <div class="all">
    <van-nav-bar
      title="选择食物"
      fixed
      left-arrow
      right-text="完成"
      @click-left="onClickLeft"
      @click-right="onClickRight"
    />
    <van-search v-model="value" placeholder="请输入食物名称" show-action>
      <template #action>
        <div @click="onSearch">搜索</div>
      </template>
    </van-search>
    <div class="pp" @click="toAdvice"> 没有找到想找的食物? 去反馈➡</div>
    <div class="foodcard" v-for="(food,i) in Allfood" @click="select(food)">
      <img :src="food.food_pic" class="foodimg" />
      <p class="foodname">{{food.food_name}}</p>
      <p class="foodheat">
        <span style="color:red">{{food.food_heat}}</span>千卡/100克
      </p>
      <div v-if="food.food_heat>200" class="foodicon" style="background:red"></div>
      <div
        v-if="food.food_heat>=100&&food.food_heat<=200"
        class="foodicon"
        style="background:orange"
      ></div>
      <div v-if="food.food_heat<100" class="foodicon" style="background:green"></div>
    </div>
    <van-action-sheet v-model="show">
      <van-picker
        show-toolbar
        :title="selectfood.food_name"
        :columns="columns"
        @confirm="onConfirm"
      />
    </van-action-sheet>
  </div>
</template>

<script>
import Vue from "vue";
import { Toast } from "vant";

Vue.use(Toast);
export default {
  data() {
    return {
      Allfood: {},
      value: "",
      selectfood: {},
      show: false,
      columns: [
        // 第一列
        {
          values: ["早餐", "午餐", "晚餐"],
          defaultIndex: 0
        },
        {
          values: ["1", "2", "3", "4", "5", "6"],
          defaultIndex: 1
        },
        // 第二列
        {
          values: ["百克", "碗", "勺"],
          defaultIndex: 0
        }
      ]
    };
  },
  methods: {
    onClickLeft() {
      this.$router.push("home");
    },
    onClickRight() {
      this.$router.push("home");
    },
    toAdvice(){
      this.$router.push("advice");
    },
    select(food) {
      this.selectfood = food;
      this.show = true;
    },
    onConfirm(value, index) {
      if (value[0] == "早餐") value[0] = "breakfast";
      if (value[0] == "午餐") value[0] = "lunch";
      if (value[0] == "晚餐") value[0] = "dinner";
      if (value[2] == "百克") value[1] = value[1] * 100;
      if (value[2] == "碗") value[1] = value[1] * 300;
      if (value[2] == "勺") value[1] = value[1] * 6;
      //获取当前时间
      var date = new Date();
      var seperator1 = "-";
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate;
      console.log(currentdate);
      console.log(value);
      //调用添加接口
      var id = localStorage.getItem("id");
      if (value[0] == "breakfast") {
        this.$axios
          .post(
            "prod-api/user/addUserDiet?" +
              "userid=" +
              id +
              "&breakfast=" +
              this.selectfood.food_name +
              "&date=" +
              currentdate +
              "&weight=" +
              value[1] +
              "&food_heat=" +
              this.selectfood.food_heat +
              "&food_pic=" +
              this.selectfood.food_pic +
              "&allheat=" +
              (this.selectfood.food_heat * value[1]) / 100
          )
          .then(res => {
            console.log(res);
            if (200 == res.status) {
              Toast.success("添加成功");
              this.show = false;
            } else {
              Toast.fail("添加失败");
            }
          });
      }
      if (value[0] == "lunch") {
        this.$axios
          .post(
            "prod-api/user/addUserDiet?" +
              "userid=" +
              id +
              "&lunch=" +
              this.selectfood.food_name +
              "&date=" +
              currentdate +
              "&weight=" +
              value[1] +
              "&food_heat=" +
              this.selectfood.food_heat +
              "&food_pic=" +
              this.selectfood.food_pic +
              "&allheat=" +
              (this.selectfood.food_heat * value[1]) / 100
          )
          .then(res => {
            console.log(res);
            if (200 == res.status) {
              Toast.success("添加成功");
              this.show = false;
            } else {
              Toast.fail("添加失败");
            }
          });
      }
      if (value[0] == "dinner") {
        this.$axios
          .post(
            "prod-api/user/addUserDiet?" +
              "userid=" +
              id +
              "&dinner=" +
              this.selectfood.food_name +
              "&date=" +
              currentdate +
              "&weight=" +
              value[1] +
              "&food_heat=" +
              this.selectfood.food_heat +
              "&food_pic=" +
              this.selectfood.food_pic +
              "&allheat=" +
              (this.selectfood.food_heat * value[1]) / 100
          )
          .then(res => {
            console.log(res);
            if (200 == res.status) {
              Toast.success("添加成功");
              this.show = false;
            } else {
              Toast.fail("添加失败");
            }
          });
      }
    },
    onSearch() {
      this.$axios
        .get("prod-api/food/searchAllFood" + "?" + "food_name=" + this.value)
        .then(res => {
          console.log(res);
          if (200 == res.status) {
            this.Allfood = res.data;
            console.log(this.Allfood);
          } else {
            console.log(res);
          }
        });
    }
  },
  mounted() {
    this.$axios.get("prod-api/food/getAllFood").then(res => {
      console.log(res);
      if (200 == res.status) {
        this.Allfood = res.data;
        console.log(this.Allfood);
      } else {
        console.log(res);
      }
    });
  }
};
</script>
<style >
.van-nav-bar .van-icon {
  color: #02e2c4;
}
.van-nav-bar__text {
  color: #02e2c4;
}
</style>
<style scoped>
.all {
  margin: 0;
  margin-top: -4%;
}
.undercard {
  position: relative;
  width: 100%;
  height: 70px;
}
.foodcard {
  margin-top: 1px;
  width: 100%;
  height: 80px;
  background: white;
  position: relative;
}
.foodimg {
  position: absolute;
  left: 30px;
  width: 70px;
  top: 5px;
  height: 70px;
}
.foodname {
  position: absolute;
  top: -10px;
  left: 95px;
  width: 100px;
  font-weight: 500;
}
.foodheat {
  position: absolute;
  top: 35px;
  left: 70px;
  width: 200px;
  color: grey;
  font-size: 13px;
}
.foodicon {
  position: absolute;
  top: calc(50% - 11px);
  right: 30px;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
}
.content {
  padding: 25px 16px 160px;
}
.pp{
  background: white;
  color: greenyellow;
  height: 35px;
  font-size:18px;
}
</style>                            