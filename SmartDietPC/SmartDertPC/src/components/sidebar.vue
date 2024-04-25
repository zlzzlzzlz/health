<template>
  <div id="sidebar">
    <el-row class="tac" style="height:230px;">
      <el-col style="width:280px;">
        <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          background-color="#04294b"
          text-color="#fff"
          mode="horizontal"
          active-text-color="#ffd04b"
          router
        >
          <el-menu-item index="0" class="he">
            <span slot="title">
              <img src="../assets/123.jpg" style="margin-left:-155px" />
            </span>
          </el-menu-item>
          <el-menu-item index="/home/userManages" class="he">
            <span slot="title" style="font-size:20px">用户管理</span>
          </el-menu-item>
          <el-menu-item index="/home/customerManages" class="he">
            <span slot="title" style="font-size:20px">菜品管理</span>
          </el-menu-item>
          <el-menu-item index="/home/boxManages" class="he">
            <span slot="title" style="font-size:20px">文章管理</span>
          </el-menu-item>
          <!-- <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-box" style="margin-left:-155px;font-size:20px"></i>
              <span style="font-size:20px">文章管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/boxManages">
                <i class="el-icon-rank" style="margin-left:-35px;font-size:20px"></i>
                <span style="font-size:17px">箱子规格</span>
              </el-menu-item>
              <el-menu-item index="/box2Manages">
                <i class="el-icon-orange" style="margin-left:-35px;font-size:20px"></i>
                <span style="font-size:17px">箱子信息</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu> -->

          <!-- <el-menu-item index="/home/orderManages" class="he">
            <i class="el-icon-setting" style="margin-left:-155px;font-size:20px"></i>
            <span slot="title" style="font-size:20px">订单管理</span>
          </el-menu-item>
          <el-menu-item index="/home/adManages" class="he">
            <i class="el-icon-menu" style="margin-left:-155px;font-size:20px"></i>
            <span slot="title" style="font-size:20px">广告管理</span>
          </el-menu-item>
          <el-menu-item index="/home/opinionManages" class="he">
            <i class="el-icon-s-order" style="margin-left:-155px;font-size:20px"></i>
            <span slot="title" style="font-size:20px">意见管理</span>
          </el-menu-item> -->
          <el-menu-item index="7" class="he2" disabled></el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "sidebar"
};
</script>

<style scoped>
.he {
  height: 75px;
  line-height: 75px;
}
.he:hover{
  color:greenyellow;
}
.he2 {
  height: 600px;
}
</style>
<style>
.el-submenu__title {
  vertical-align: middle;
  height: 75px;
  line-height: 75px;
}
</style>