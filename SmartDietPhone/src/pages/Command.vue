<template>
  <div class="alldiv">
    <div>
      <van-nav-bar
        title="饮食建议"
        left-arrow
        fixed
        @click-left="onClickLeft"
      />
    </div>
    <div class="all">
      <img class="img" src="../assets/command.png" />
      <div class="upcard">
        <p class="p1" v-if="userBase.weight_type==1">您的目标是减脂/减肥,推荐全麦面包，玉米，米饭（少量一次1到2两）鸡蛋，鸭蛋、豆浆，牛奶（少量），苹果，没甜味的菜瓜，黑木耳，西红柿，黄瓜，冬瓜，苦瓜，海带，芹菜，韭菜，紫菜头，豆腐，豆皮，鱼虾鸡肉（少量），和一切蔬菜。(这些蔬菜搭配着，轮流吃）切记不要节食，节食的后果会造成营养不良内分泌失调起痘痘或皮肤很差,营养不良。</p>
        <p class="p1" v-if="userBase.weight_type==2">您的目标是塑性/保持，推荐摄入含水量较高的食物，如水果、蔬菜、汤、动物胶原蛋白、全麦类植物等，注意营养均衡并注意糖类控制。</p>
         <p class="p1" v-if="userBase.weight_type==3">您的目标是增肥，推荐摄入含水量较高的食物，如水果、蔬菜、汤、动物胶原蛋白、全麦类植物等，注意营养均衡并注意糖类控制。</p>
          </div>
      </div>
      <div class="yeah">
        
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userBase: [],
      bmi: 0,
      height:0,
      weight:0,
    };
  },
  methods: {
    onClickLeft() {
      this.$router.push("me");
    },
    getuserBase() {
      var id = localStorage.getItem("id");
      this.$axios.get("prod-api/user/getUserBase?userid=" + id).then((res) => {
        if (200 == res.status) {
          this.userBase = res.data;
          this.height=res.data.height/100;
          this.weight=res.data.weight;
          var temp=parseFloat(this.height)*parseFloat(this.height)
        //   this.bmi=(this.weight.toFixed(2)/((this.height.toFixed(2))*(this.height.toFixed(2))));
          this.bmi=(parseFloat(this.weight)/temp).toFixed(2);
        } else {
          console.log("null");
        }
      });
    //   this.bmi=(parseFloat(this.userBase.weight)/(parseFloat(this.userBase.height)*parseFloat(this.userBase.height)));
    //   var height=parseInt(this.userBase.height);
    },
  },
  mounted() {
    this.getuserBase();
  },
};
</script>


<style scoped>
.alldiv {
  margin: 0;
  padding: 0;
  height: 100vh;
  background: white;
  margin-top: -4%;
}
.all {
  position: relative;
}
.img {
  position: absolute;
  height: 180px;
  width: 100%;
  top: 2px;
  left: 1px;
}
.upcard{
  top: 110px;
  left:30px;
  position: absolute;
  background: white;
  margin: 0 auto;
  width: calc(100% - 50px);
  height: 330px;
  display: flex;
  box-shadow: 2px 2px 20px #9b9a9a;
}
.p1{
    font-size: 18px;
    font-weight: 600;
    position: absolute;
    left:3px;
}
</style>