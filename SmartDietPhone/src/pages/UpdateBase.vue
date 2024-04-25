<template>
  <div class="alldiv">
    <div>
      <van-nav-bar title="修改健康资料" left-arrow fixed @click-left="onClickLeft" />
    </div>
    <van-form @submit="onSubmit" style="margin-top:-12px">
      <van-field name="slider" label="身高" style="margin-top:2%;">
        <template #input>
          <van-slider v-model="height" active-color="#02e2c4" :min="150" :max="195">
            <template #button>
              <div class="custom-button">{{ height }}cm</div>
            </template>
          </van-slider>
        </template>
      </van-field>
      <van-field name="slider" label="体重" style="margin-top:2%;">
        <template #input>
          <van-slider v-model="weight" active-color="#02e2c4" :min="35" :max="120">
            <template #button>
              <div class="custom-button">{{ weight }}kg</div>
            </template>
          </van-slider>
        </template>
      </van-field>
      <van-field
        readonly
        clickable
        name="picker"
        :value="radio"
        label="身材管理路线:"
        placeholder="点击选择"
        @click="showPicker = true"
      />
      <van-popup v-model="showPicker" position="bottom">
        <van-picker
          show-toolbar
          :columns="columns"
          @confirm="onConfirm"
          @cancel="showPicker = false"
        />
      </van-popup>
      <div style="margin: 16px;">
        <van-button
          round
          block
          plain
          type="primary"
          native-type="submit"
          style="width:150px;margin-left:30%;margin-top:10%;"
        >提交</van-button>
      </div>
    </van-form>
    <img src="../assets/height.png" style="weight:200px;height:400px;margin-left:70px" />
  </div>
</template>

<script>
import Vue from "vue";
import { NavBar } from "vant";

Vue.use(NavBar);
export default {
  data() {
    return {
      height: 177,
      weight: 70,
      radio: "减肥",
      columns: ["减肥", "保持/塑性", "增肥"],
      showPicker: false
    };
  },
  methods: {
    onConfirm(radio) {
      this.radio = radio;
      this.showPicker = false;
    },
    onSubmit() {
      var id = localStorage.getItem("id");
      var weight_type = "";
      if (this.radio == "减肥") weight_type = 1;
      if (this.radio == "保持/塑性") weight_type = 2;
      if (this.radio == "增肥") weight_type = 3;
      this.$axios
        .post(
          "prod-api/user/updateUserBase?userid=" +
            id +
            "&height=" +
            this.height +
            "&weight=" +
            this.weight +
            "&weight_type=" +
            weight_type
        )
        .then(res => {
          console.log(res);
          if ("success" == res.data) {
            this.$toast.success("保存成功");
          } else {
            this.$toast.fail("保存失败");
          }
        });
    },
    onClickLeft() {
      this.$router.push("me");
    }
  }
};
</script>

<style scoped>
.alldiv {
  margin: 0;
  padding: 0;
  height: 100vh;
  background: white;
}
.custom-button {
  width: 50px;
  color: #fff;
  font-size: 10px;
  line-height: 18px;
  text-align: center;
  background-color: #02e2c4;
  border-radius: 100px;
}
</style>