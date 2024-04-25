<template>
  <div class="alldiv">
    <div>
      <van-nav-bar
        title="食物营养"
        left-arrow
        fixed
        @click-left="onClickLeft"
      />
      <van-search v-model="value" placeholder="请输入食物名称" show-action style="height:80px">
        <template #action>
          <div @click="getfood">搜索</div>
        </template>
      </van-search>
      <p style="font-size:18px">{{foodlist2.name}}</p>
      <p style="font-size:18px" v-if="err==1">搜索失败，请重新搜索</p>
      <img class="img" src="../assets/pic11.png"  v-if="foodlist2.name==null" style="width:85%"/>
     <van-cell-group style="margin-left:-70px" v-if="foodlist2.name">
         <van-cell title="食物种类" :value="foodlist2.type"  center/>
        <van-cell title="热量" :value="foodlist2.rl" center/>
        <van-cell title="蛋白质" :value="foodlist2.dbz"  center/>
        <van-cell title="碳水化合物" :value="foodlist2.shhf"  center/>
        <van-cell title="膳食纤维" :value="foodlist2.ssxw"  center/>
        <van-cell title="钙" :value="foodlist2.gai"  center/>
        <van-cell title="维生素C" :value="foodlist2.wsfc"  center/>
    </van-cell-group>
    <p style="font-size:18px;color:red" v-if="foodlist2.rl>200">热量偏多，少吃一点哦~</p>
    <p style="font-size:18px;color:green" v-if="foodlist2.rl>=100&&foodlist2.rl<=200">热量刚刚好，推荐食用~</p>
    <p style="font-size:18px;color:green" v-if="foodlist2.rl<100">热量很少，放心吃~</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      foodlist: [],
      foodlist2: [],
      name:"",
      value: "",
      err:0,
    };
  },
  methods: {
    onClickLeft() {
      this.$router.push("home");
    },
    getfood() {
      var request = require("request");
      var that = this;
      that.err=0;
      that.foodlist=[];
      that.foodlist2=[];
      request.get(
        "http://api.tianapi.com/txapi/nutrient/index?key=96ef1cf7ab18a1c87a244c7c97c16019&word=" +
          this.value +
          "&mode=0",
        function (err, response, body) {
          if(JSON.parse(body).code==250){
            that.err=1;
          }
          else{
          that.foodlist = JSON.parse(body);
          that.foodlist2 = that.foodlist.newslist[0];
          }
        }
      );
    //   this.getnew();
    },
    getnew(){
        this.name=this.foodlist2.name;
        console.log(this.foodlist2)
    }
  },
  mounted() {
    // this.getfood();
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
.center {
  float: left;
  margin-left: 30px;
}
</style>