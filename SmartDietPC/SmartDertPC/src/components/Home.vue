<template>
  <div class="wrapper">
    <!-- <v-sidebar></v-sidebar> -->
     <v-head></v-head>
    <div class="content-box">
      <div class="app-wrap">
        <div class="content">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
//引入两个页面
import vHead from "./Header.vue";
import vSidebar from "./sidebar.vue";
export default {
  name: "home",
  components: {
    vHead,
    vSidebar
  },
  data() {
    return {};
  },
  computed: {}
};
</script>

<style lang="scss" scoped>
.content-box {
  margin-top: -235px;
}
.app-wrap {
  height: 96%;
}
.content {
  width: 92%;
  height: 100%;
  margin: 10px;
  display: block;
  position: absolute;
  left: 60px;
  top: 70px;
  bottom: 0;
}
</style>