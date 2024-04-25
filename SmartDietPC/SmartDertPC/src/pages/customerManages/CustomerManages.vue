<template>
  <div class="user">
    <div class="formData">
      <el-card class="searchModule">
        <el-form :model="user" label-width="100px" :inline="true" :ref="user">
          <el-form-item label="菜品查询" prop="name" style="margin-left:-190px">
            <el-input v-model="user.name" clearable></el-input>
          </el-form-item>
          <el-button type="primary" @click="onSearch()" style="margin-left:40px">查询</el-button>
          <el-button type="primary" @click="resetForm(user)">重置</el-button>
        </el-form>
      </el-card>
    </div>
    <div class="contentB">
      <div class="clickBtn">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-button class="operateBtn" type="primary" @click="addData">新增</el-button>
        <el-button type="primary" @click="editData">修改</el-button>
        <el-button type="primary" @click="delData()">删除</el-button>
      </div>
      <div class="tableData">
        <sirui-table
          class="operateTable"
          :page="page"
          :loading="load"
          :data="tableData"
          @handleSelectionChange="handleSelectionChange"
          @handleSizeChange="handleSizeChange"
          @handleCurrentChange="handleCurrentChange"
          :tableHeight="tableHeight"
        >
          <el-table-column align="center" type="selection" width="45"></el-table-column>
          <el-table-column
            v-for="(item,index) in columnList"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :width="item.width"
            :sortable="item.sort"
            :fixed="item.fixed"
            align="center"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column prop="food_pic" label="菜品图片" min-width="70" height="55">
            <!--图片显示-->
            <template slot-scope="scope">
              <img :src="scope.row.food_pic" min-width="70" height="55" />
            </template>
          </el-table-column>
        </sirui-table>
      </div>
    </div>
    <AdAdd ref="addData" v-on:search="search"></AdAdd>
    <AdEdit ref="editData" v-on:search="search"></AdEdit>
  </div>
</template>

<script>
import SiruiTable from "@/components/SiruiTable";
// import CustomerAdd from "@/pages/CustomerManages/CustomerAdd";
// import CustomerEdit from "@/pages/CustomerManages/CustomerEdit";
import AdAdd from "@/pages/AdManages/AdAdd";
import AdEdit from "@/pages/AdManages/AdEdit";
export default {
  name: "CustomerManages",
  components: {
    SiruiTable,
    AdAdd,
    AdEdit
  },
  data() {
    return {
      user: {
        name: "",
      },
      page: {
        pages: 0,
        pageNum: 1,
        pageSize: 10,
        total: 0
      },
      load: false,
      multipleSelection: [],
      columnList: [
        {
          prop: "food_name",
          label: "菜品名称",
          width: 200
        },
        { prop: "food_heat", label: "菜品热量" },
        { prop: "food_pic", label: "图片地址" },
      ],
      tableData: [
      ],
      tableHeight: 400
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    //查询
    search() {
      this.getData();
    },
    onSearch() {
      this.$axios
        .get("prod-api/food/searchAllFood" + "?" + "food_name=" + this.user.name)
        .then(res => {
          console.log(res);
          if (200 == res.status) {
            this.tableData = res.data;
            console.log(this.tableData);
          } else {
            console.log(res);
          }
        });
    },
    getData() {
      this.$axios
        .get(
          "prod-api/food/getAllFood" 
        )
        .then(res => {
          //this.loading.endLoading();
          if (200 == res.status) {
            // for (var i = 0; i < res.data.length; i++) {
            //   if (res.data[i].food_pic) {
            //     res.data[i].food_pic = "prod-api/" + res.data[i].food_pic;
            //   }
            // }
            this.tableData = res.data;
            console.log(this.tableData);
            this.$message({
              showClose: true,
              message: "查询成功",
              type: "success"
            });
          } else {
            this.$message({
              showClose: true,
              message: "查询失败",
              type: "error"
            });
          }
        });
    },
    //重置
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
      }
    },
    //选中触发事件
    handleSelectionChange(row) {
      this.multipleSelection = row;
    },
    //每条页数
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.getData();
    },
    //第几页
    handleCurrentChange(val) {
      this.page.pageNum = val;
      this.getData();
    },
    //新增按钮
    addData() {
      //防止子组件的值影响父组件
      this.$refs.addData.openDialog(true);
    },
    editData() {
      if (1 == this.multipleSelection.length) {
        //防止子组件的值影响父组件
        this.$refs.editData.openDialog(
          true,
          JSON.parse(JSON.stringify(this.multipleSelection[0]))
        );
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行修改",
          type: "error"
        });
      }
    },
    isopen() {
      if (this.multipleSelection.length >= 1) {
        if (0 != this.multipleSelection.length) {
          if (this.multipleSelection[0].status == "0") {
            alert("已启用，不可启用");
          } else {
            this.$confirm("是否启用?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            })
              //调用接口
              .then(() => {
                let params = {
                  userIds: []
                };
                for (var i = 0; i < this.multipleSelection.length; i++) {
                  params.userIds.push(this.multipleSelection[i].userId);
                }
                params.userIds = params.userIds.join(",");
                this.$axios
                  .put("prod-api/backend/customer/enable/" + params.userIds)
                  .then(res => {
                    // this.loading.endLoading();
                    if (200 == res.data.code) {
                      //this.searchForm();
                      this.$message({
                        type: "success",
                        message: res.data.msg
                      });
                    }
                  });
              })
              .catch(() => {
                this.$message({
                  type: "info",
                  message: "已取消启用"
                });
              });
          }
        } else {
          this.$message({
            showClose: true,
            message: "请选择数据进行操作",
            type: "error"
          });
        }
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行操作",
          type: "error"
        });
      }
    },
    isclose() {
      if (this.multipleSelection.length >= 1) {
        if (0 != this.multipleSelection.length) {
          if (this.multipleSelection[0].status == "1") {
            alert("已停用，不可停用");
          } else {
            this.$confirm("是否停用?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            })
              //调用接口
              .then(() => {
                let params = {
                  userIds: []
                };
                for (var i = 0; i < this.multipleSelection.length; i++) {
                  params.userIds.push(this.multipleSelection[i].userId);
                }
                params.userIds = params.userIds.join(",");
                this.$axios
                  .put("prod-api/backend/customer/disable/" + params.userIds)
                  .then(res => {
                    // this.loading.endLoading();
                    if (200 == res.data.code) {
                      //this.searchForm();
                      this.$message({
                        type: "success",
                        message: res.data.msg
                      });
                    }
                  });
              })
              .catch(() => {
                this.$message({
                  type: "info",
                  message: "已取消停用"
                });
              });
          }
        } else {
          this.$message({
            showClose: true,
            message: "请选择数据进行操作",
            type: "error"
          });
        }
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行操作",
          type: "error"
        });
      }
    },
    delData() {
      if (this.multipleSelection.length >= 1) {
        if (0 != this.multipleSelection.length) {
          if (this.multipleSelection[0].status == "0") {
            alert("启用中，不可删除");
          } else {
            this.$confirm("此操作会永久删除数据，是否继续?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            })
              //调用接口
              .then(() => {
                console.log(this.multipleSelection)
                this.$axios
                  .delete("prod-api/food/delFood?food_id=" + this.multipleSelection[0].food_id)
                  .then(res => {
                    // this.loading.endLoading();
                    if (200 == res.status) {
                      //this.searchForm();
                      this.$message({
                        type: "success",
                        message: "删除成功"
                      });
                      this.getData();
                    }
                  });
              })
              .catch(() => {
                this.$message({
                  type: "info",
                  message: "已取消删除"
                });
              });
          }
        } else {
          this.$message({
            showClose: true,
            message: "请选择数据进行操作",
            type: "error"
          });
        }
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行操作",
          type: "error"
        });
      }
    }
    //
  }
};
</script>

<style scoped>
.contentB {
  background: white;
  margin-top: 60px;
}
.user {
  margin-left: 55px;
}
.clickBtn {
  top: 115px;
  background: white;
  position: absolute;
  width: 1345px;
  left: 50px;
  text-align: left;
  height: 50px;
  line-height: 50px;
}
.tableData {
  margin-top: 20px;
}
</style>