<template>
  <div class="alldiv">
    <div>
      <van-nav-bar
        title="健康报告"
        left-arrow
        fixed
        @click-left="onClickLeft"
      />
    </div>
    <div class="all">
      <img class="img" src="../assets/back2.png" />
      <div class="upcard">
          <p class="p1">体重: {{userBase.weight}} Kg</p>
          <p class="p2">BMI:  {{bmi}}</p>
          <div class="f1" >
            <p style="background:#b1ffaa"> 分类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;BMI范围</p>
           <img class="img3" src="../assets/arrow.png" v-if="bmi<=18.4"/> <p>偏瘦&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<  &nbsp;= &nbsp;18.4</p>
            <img class="img3" src="../assets/arrow.png" v-if="bmi>=18.5&&bmi<=23.9"/> <p>正常&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;18.5 ~ 23.9</p>
           <img class="img3" src="../assets/arrow.png" v-if="bmi>=24&&bmi<=27.9"/>  <p>过重&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;24.0 ~ 27.9</p>
           <img class="img3" src="../assets/arrow.png" v-if="bmi>=28"/>  <p>肥胖&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>  &nbsp;= &nbsp;28.0</p>
           <p v-if="bmi<=18.4" style="margin-top:10%">您的体重偏瘦</p>
           <p v-if="bmi>=18.5&&bmi<=23.9" style="margin-top:10%">您的体重正常，继续保持~</p>
           <p v-if="bmi>=24&&bmi<=27.9" style="margin-top:10%">您的体重过重，需要减肥~</p>
           <p v-if="bmi>=28" style="margin-top:10%">您的体重属于肥胖，需要减肥~</p>
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
.yeah{
  top: 410px;
  left:30px;
  position: absolute;
}
.p1{
    font-size: 18px;
    position: absolute;
    left:25px;
}
.p2{
    font-size: 18px;
    position: absolute;
    left:25px;
    top:40px;
}
.f1{
    position:absolute;
    background:#b1ffaa;
    height:200px;
    width:250px;
    top:80px;
    left:85px;
}
.img3{
  position: absolute;
  left:-40px;
  height: 30px;
}
</style>